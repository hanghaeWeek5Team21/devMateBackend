package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.aws.s3.S3Object;
import com.sparta.devmatebackend.config.StorageConfig;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.exception.file.FileNoExtensionException;
import com.sparta.devmatebackend.exception.file.FileOverMaxSizeException;
import com.sparta.devmatebackend.exception.file.FileRenameException;
import com.sparta.devmatebackend.service.StorageService;
import com.sparta.devmatebackend.utils.FileNameUtils;
import com.sparta.devmatebackend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final Long MAX_FILE_BYTES = 536870912L;
    private final StorageService storageService;
    private final S3Object s3Object;
    private final StorageConfig storageConfig;

    @GetMapping("api/file/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        // 파일을 서버에서 다운로드 받기
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("api/file/image")
    public ResMesResultResponseDto handleImageUpload(@RequestParam("file") MultipartFile file) {
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        try {
            // 파일의 크기를 확인합니다.
            if (storageService.fileOverSize(file, MAX_FILE_BYTES)) {
                throw new FileOverMaxSizeException("파일이 512MB를 넘습니다.");
            }

            // 파일을 저장합니다.
            File savedFile = storageService.store(file);

            // 파일의 모든 이름, 확장자, 위치를 가져옵니다.
            String fileName = savedFile.getName();
            String fileExtension = FileNameUtils.getExtensionFromFileName(savedFile.getName())
                    .orElseThrow(() -> new FileNoExtensionException("파일에 확장자가 없습니다."));
            String fileLocation = "./" + storageConfig.getLocation() + "/";

            // 파일이 s3에 중복되는지 확인합니다.
            while (s3Object.objectExists(fileName)) {
                // 중복되면 파일명을 다르게 변경합니다.
                fileName = StringUtils.generateRandomString() + fileExtension;
                // 변경된 파일명을 적용합니다.
                if (savedFile.renameTo(new File( fileLocation + fileName))) {
                    savedFile = new File(fileLocation + fileName);
                }else{
                    throw new FileRenameException("파일의 이름을 바꾸지 못합니다.");
                }
            }

            // 저장된 파일을 s3에 업로드합니다.
            s3Object.upload(savedFile);

            // 업로드된 파일 URL 을 받습니다.
            String url = s3Object.getObjectUrl(savedFile);

            resDto.setRes(true);
            resDto.setMsg("파일이 서버에 저장되었습니다.");
            resDto.setResult(url);

        } catch (Exception e) {
            resDto.setRes(false);
            resDto.setMsg(e.getMessage());
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return resDto;
    }
}