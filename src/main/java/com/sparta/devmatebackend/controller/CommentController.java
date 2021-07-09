package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentPutRequestDto;
import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.dto.CommentResponseDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("api/comment")
    public CommentResponseDto create(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto respDto = new CommentResponseDto();
        try {
            commentService.save(commentRequestDto);
            respDto.setRes(true);
            respDto.setMsg("댓글이 작성되었습니다.");
        } catch (Exception e) {
            respDto.setRes(false);
            respDto.setMsg("댓글 작성에 실패하였습니다.");
        }
        return respDto;
    }

    @DeleteMapping("api/comment/{id}")
    public CommentResponseDto delete(@PathVariable Long id) {
        CommentResponseDto respDto = new CommentResponseDto();
        try {
            commentService.delete(id);
            respDto.setRes(true);
            respDto.setMsg("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            respDto.setRes(false);
            respDto.setMsg("댓글 삭제에 실패하였습니다.");
        }
        return respDto;
    }

    @PutMapping("api/comment/{id}")
    public CommentResponseDto update(@PathVariable Long id, CommentPutRequestDto requestDto) {
        CommentResponseDto respDto = new CommentResponseDto();
        try {
            commentService.update(id, requestDto);
            respDto.setRes(true);
            respDto.setMsg("댓글이 수정되었습니다.");
        } catch (Exception e) {
            respDto.setRes(false);
            respDto.setMsg("댓글 수정에 실패하였습니다.");
        }
        return respDto;
    }

    @GetMapping("api/comment")
    public List<Comment> getComment(@RequestParam(value = "user_id") Long user_id) {
        return commentRepository.findByUserIdOrderByCreatedAtDesc(user_id);
    }

}
