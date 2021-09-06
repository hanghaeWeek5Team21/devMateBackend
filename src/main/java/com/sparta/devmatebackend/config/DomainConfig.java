package com.sparta.devmatebackend.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("domain")
@ConfigurationProperties(prefix = "config.domain")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DomainConfig {
    private final String fullName;
}
