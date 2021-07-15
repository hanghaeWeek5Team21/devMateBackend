package com.sparta.devmatebackend.exception.file;

public class FileOverMaxSizeException  extends RuntimeException{
    public FileOverMaxSizeException(String message) {
        super(message);
    }
}
