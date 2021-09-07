package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.requestDto.CommentUpdateRequestDto;
import com.sparta.devmatebackend.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // create
    @Transactional
    public void create(CommentCreateRequestDto requestDto, UserDetailsImpl userDetails){
        Comment comment = new Comment(
                requestDto.getContents(),
                userRepository.getById(requestDto.getUser_id()),
                userDetails.getUser()
        );
        commentRepository.save(comment);
    }
    // read

    // update
    @Transactional
    public void update(Long id, CommentUpdateRequestDto commentUpdateRequestDto, UserDetailsImpl userDetails) throws IllegalAccessException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        if (!comment.getAuthor().getId().equals(userDetails.getUser().getId())){
            throw new IllegalAccessException("로그인된 유저가 작성한 댓글이 아닙니다.");
        }
        comment.update(commentUpdateRequestDto);
    }

    //delete
    @Transactional
    public void delete(Long id, UserDetailsImpl userDetails) throws IllegalAccessException {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        if (!comment.getAuthor().getId().equals(userDetails.getUser().getId())){
            throw new IllegalAccessException("로그인된 유저가 작성한 댓글이 아닙니다.");
        }

        commentRepository.delete(comment);
    }
}
