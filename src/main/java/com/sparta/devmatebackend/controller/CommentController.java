package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("api/comment")
    public Comment create(@RequestBody CommentRequestDto commentRequestDto){
        Comment comment = new Comment(commentRequestDto);
        return commentRepository.save(comment);
    }

    @DeleteMapping("api/comment/{comment_id}")
    public void delete(@PathVariable Long id){
        commentService.delete(id);
    }
}
