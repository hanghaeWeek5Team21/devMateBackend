package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentResponseDto;
import com.sparta.devmatebackend.dto.LikePutRequestDto;
import com.sparta.devmatebackend.repository.LikesRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesRepository likesRepository;
    private final LikesService likesService;

    @PostMapping("api/likes")
    // TODO : ResponseEntity 로 변경하기
    public CommentResponseDto create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LikePutRequestDto likePutRequestDto) {
        CommentResponseDto commentResponseDto;
        try {
            likesService.create(likePutRequestDto, userDetails);
            commentResponseDto = new CommentResponseDto(true, "좋아요가 작성되었습니다.");
        } catch (Exception e) {
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    @DeleteMapping("api/likes/{id}")
    // TODO : ResponseEntity 로 변경하기
    public CommentResponseDto delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        CommentResponseDto commentResponseDto;
        try {
            likesService.delete(id, userDetails);
            commentResponseDto = new CommentResponseDto(true, "좋아요가 삭제되었습니다.");
        } catch (Exception e) {
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }
}
