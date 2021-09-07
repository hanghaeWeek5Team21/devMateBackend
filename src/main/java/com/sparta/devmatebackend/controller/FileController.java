package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.service.FileService;
import lombok.AccessLevel;
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
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FileController {
    private final FileService fileService;

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