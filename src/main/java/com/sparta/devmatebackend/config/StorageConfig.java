package com.sparta.devmatebackend.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("storage")
@ConfigurationProperties(prefix = "config.storage")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StorageConfig {
    private final String location;
}