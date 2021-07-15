package com.sparta.devmatebackend.utils;

import java.util.UUID;

public abstract class StringUtils {
    public static String generateRandomString(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
