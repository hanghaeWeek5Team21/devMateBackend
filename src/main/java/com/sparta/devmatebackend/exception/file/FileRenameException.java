package com.sparta.devmatebackend.exception.file;

public class FileRenameException  extends RuntimeException{
    private static final String MESSAGE = "파일의 이름을 바꾸지 못합니다.";
    public FileRenameException() {
        super(MESSAGE);
    }
}
