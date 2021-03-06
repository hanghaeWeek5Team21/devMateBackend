package com.sparta.devmatebackend;

import com.sparta.devmatebackend.service.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class DevMateBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevMateBackendApplication.class, args);
    }

    // 시작할 때
    @Bean
    CommandLineRunner init(FileService fileService) {
        return (args) -> {
            // 모든 파일을 삭제합니다.
            fileService.deleteAll();
            fileService.init();
        };
    }
}
