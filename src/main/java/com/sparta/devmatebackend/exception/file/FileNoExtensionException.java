package com.sparta.devmatebackend.exception.file;

public class FileNoExtensionException  extends RuntimeException{
    public FileNoExtensionException(String message) {
        super(message);
    }
}