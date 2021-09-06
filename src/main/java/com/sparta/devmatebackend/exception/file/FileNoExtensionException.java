package com.sparta.devmatebackend.exception.file;

public class FileNoExtensionException extends RuntimeException{
    private static final String MESSAGE = "파일에 확장자가 없습니다.";
    public FileNoExtensionException() {
        super(MESSAGE);
    }
}