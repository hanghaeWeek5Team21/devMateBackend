package com.sparta.devmatebackend;

import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Collection;

@EnableJpaAuditing
@SpringBootApplication
public class DevMateBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevMateBackendApplication.class, args);
    }

    // 시작할 때
    @Bean
    CommandLineRunner init(StorageService storageService, UserRepository userRepository) {
        return (args) -> {
            // 모든 파일을 삭제합니다.
            storageService.deleteAll();
            storageService.init();
        };
    }
}
