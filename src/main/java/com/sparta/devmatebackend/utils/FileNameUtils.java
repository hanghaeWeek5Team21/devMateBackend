package com.sparta.devmatebackend.utils;

import java.util.Optional;

public abstract class FileNameUtils {
    public static Optional<String> getExtensionFromFileName(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".")));
    }
}
