package com.sparta.devmatebackend.exception.file;

public class FileOverMaxSizeException  extends RuntimeException{
    private static final String MESSAGE_PREFIX = "파일 크기가 ", MESSAGE_POSTFIX = "를 넘습니다.";
    public FileOverMaxSizeException(Long maxSizeOfMegabytes) {
        super(MESSAGE_PREFIX + maxSizeOfMegabytes + MESSAGE_POSTFIX);
    }
}
