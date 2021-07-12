package com.sparta.devmatebackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("domain")
@ConfigurationProperties(prefix = "config.domain")
@Getter
@Setter
public class DomainConfig {
    private String fullName;
}
