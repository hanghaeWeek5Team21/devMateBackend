package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.aws.s3.S3Object;
import com.sparta.devmatebackend.config.StorageConfig;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;


@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RequiredArgsConstructor
@RestController
public class FileController {

    private static final Long MAX_FILE_BYTES = 536870912L;
    private static final Long MAX_FILES_MEGABYTES = 512L;
    private final StorageService storageService;
    private final S3Object s3Object;
    private final StorageConfig storageConfig;

    //create
    @PostMapping("api/file/image")
    public ResponseEntity<String> create(@RequestParam("file") MultipartFile file) throws IOException {
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).body(fileService.saveAndGetS3ObjectUrl(file));
    }

    //read
    @GetMapping("api/file/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> read(@PathVariable String filename) {
        // 파일을 서버에서 다운로드 받기
        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    //update

    //delete

}