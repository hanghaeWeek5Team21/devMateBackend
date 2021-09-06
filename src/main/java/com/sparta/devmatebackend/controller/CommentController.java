package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentPutRequestDto;
import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.dto.CommentResponseDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @PostMapping("api/comment")
    // TODO : ResponseEntity 로 변경하기
    public CommentResponseDto create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto;
        try {
            commentService.create(commentRequestDto, userDetails);
            commentResponseDto = new CommentResponseDto(true, "댓글이 작성되었습니다.");
        } catch (Exception e) {
            // TODO : exception 처리하는 globalExceptionController 로 처리방법 변경하기
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    @DeleteMapping("api/comment/{id}")
    public CommentResponseDto delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        CommentResponseDto commentResponseDto;
        try {
            commentService.delete(id, userDetails);
            commentResponseDto = new CommentResponseDto(true, "댓글이 삭제되었습니다.");
        } catch (Exception e) {
            // TODO : exception 처리하는 globalExceptionController 로 처리방법 변경하기
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    @PatchMapping("api/comment/{id}")
    public CommentResponseDto update(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentPutRequestDto requestDto) {
        CommentResponseDto commentResponseDto;
        try {
            commentService.update(id, requestDto, userDetails);
            commentResponseDto = new CommentResponseDto(true, "댓글이 수정되었습니다.");
        } catch (Exception e) {
            // TODO : exception 처리하는 globalExceptionController 로 처리방법 변경하기
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    @GetMapping("api/comment")
    public List<Comment> getComment(@RequestParam(value = "user_id") Long user_id) {
        User user = userRepository.getById(user_id);
        return commentRepository.findAllByUserOrderByCreatedAtDesc(user);
    }
}
