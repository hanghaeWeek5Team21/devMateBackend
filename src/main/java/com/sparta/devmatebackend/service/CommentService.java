package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.CommentPutRequestDto;
import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // create
    @Transactional
    public void create(CommentRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new IllegalArgumentException("로그인되어 있지 않습니다.");
        }
        Comment comment = new Comment();
        comment.setUser(userRepository.getById(requestDto.getUser_id()));
        comment.setContents(requestDto.getContents());
        comment.setAuthor(userDetails.getUser());
        commentRepository.save(comment);
    }
    // read

    // update
    @Transactional
    public Long update(Long id, CommentPutRequestDto commentPutRequestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        comment.update(commentPutRequestDto);
        return comment.getId();
    }
    //delete
    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        commentRepository.delete(comment);
    }
}
