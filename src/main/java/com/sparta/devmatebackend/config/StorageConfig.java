package com.sparta.devmatebackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("storage")
@ConfigurationProperties(prefix = "config.storage")
@Getter
@Setter
public class StorageConfig {
    private String location;
}