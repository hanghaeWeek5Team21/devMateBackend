package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.requestDto.LikePutRequestDto;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("api/likes")
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LikePutRequestDto likePutRequestDto) {
        likesService.create(likePutRequestDto, userDetails);
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).build();
    }

    @DeleteMapping("api/likes/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) throws IllegalAccessException {
        likesService.delete(id, userDetails);
        return ResponseEntity.ok().build();
    }
}
