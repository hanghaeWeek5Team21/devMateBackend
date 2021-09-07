package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.aws.s3.S3Object;
import com.sparta.devmatebackend.config.StorageConfig;
import com.sparta.devmatebackend.exception.file.FileNoExtensionException;
import com.sparta.devmatebackend.exception.file.FileNotImageException;
import com.sparta.devmatebackend.exception.file.FileOverMaxSizeException;
import com.sparta.devmatebackend.exception.file.FileRenameException;
import com.sparta.devmatebackend.exception.storage.StorageException;
import com.sparta.devmatebackend.exception.storage.StorageFileNotFoundException;
import com.sparta.devmatebackend.utils.FileNameUtils;
import com.sparta.devmatebackend.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {

    private final Path rootLocation;
    private final S3Object s3Object;
    private final StorageConfig storageConfig;

    private static final Long MAX_FILE_BYTES = 536870912L;
    private static final Long MAX_FILES_MEGABYTES = 512L;


    @Autowired
    public FileService(StorageConfig storageConfig, S3Object s3Object) {
        this.rootLocation = Paths.get(storageConfig.getLocation());
        this.s3Object = s3Object;
        this.storageConfig = storageConfig;
    }

    public String saveAndGetS3ObjectUrl(MultipartFile file) throws IOException {

        // 파일의 크기를 확인합니다.
        if (fileOverSize(file, MAX_FILE_BYTES)) {
            throw new FileOverMaxSizeException(MAX_FILES_MEGABYTES);
        }

        // 파일을 저장합니다.
        File savedFile = store(file);

        // 파일의 모든 이름, 확장자, 위치를 가져옵니다.
        String fileName = savedFile.getName();
        String fileExtension = FileNameUtils.getExtensionFromFileName(fileName).orElseThrow(FileNoExtensionException::new);
        String fileLocation = "./" + storageConfig.getLocation() + "/";

        // 파일이 s3에 중복되는지 확인합니다.
        while (s3Object.objectExists(fileName)) {
            // 중복되면 파일명을 다르게 변경합니다.
            fileName = StringUtils.generateRandomString() + fileExtension;
            // 변경된 파일명을 적용합니다.
            if (savedFile.renameTo(new File(fileLocation + fileName))) {
                savedFile = new File(fileLocation + fileName);
            } else {
                throw new FileRenameException();
            }
        }

        // 저장된 파일을 s3에 업로드합니다.
        s3Object.upload(savedFile);

        // 업로드된 파일 URL 을 반환합니다.
        return s3Object.getObjectUrl(savedFile);
    }

    public boolean fileOverSize(MultipartFile file, Long byteSize) {
        return file.getSize() > byteSize;
    }

    public File store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            else if (!Objects.requireNonNull(file.getContentType()).contains("image")){
                throw new FileNotImageException("Uploaded file is not an image.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return destinationFile.toFile();
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}