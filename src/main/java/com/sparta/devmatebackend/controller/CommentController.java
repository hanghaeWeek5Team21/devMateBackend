package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.requestDto.CommentUpdateRequestDto;
import com.sparta.devmatebackend.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //create
    @PostMapping("api/comment")
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        commentService.create(commentCreateRequestDto, userDetails);
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).build();
    }

    //read
    @GetMapping("api/comment")
    public ResponseEntity<List<Comment>> read(@RequestParam(value = "user_id") Long user_id) {
        User user = userRepository.getById(user_id);
        List<Comment> commentsByUser = commentRepository.findAllByUserOrderByCreatedAtDesc(user);
        return ResponseEntity.ok().body(commentsByUser);
    }

    //update
    @PatchMapping("api/comment/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentUpdateRequestDto requestDto) throws IllegalAccessException {
        commentService.update(id, requestDto, userDetails);
        return ResponseEntity.ok().build();
    }

    //delete
    @DeleteMapping("api/comment/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) throws IllegalAccessException {
        commentService.delete(id, userDetails);
        return ResponseEntity.ok().build();
    }
}
