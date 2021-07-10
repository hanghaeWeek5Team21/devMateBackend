package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.CommentPutRequestDto;
import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.repository.CommentRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, CommentPutRequestDto commentPutRequestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        comment.update(commentPutRequestDto);
        return comment.getId();
    }

    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        commentRepository.delete(comment);
    }

    @Transactional
    public void save(CommentRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null){
            throw new IllegalArgumentException("로그인되어 있지 않습니다.");
        }
        Comment comment = new Comment(requestDto);
        comment.setAuthor_id(userDetails.getUser().getId());
        commentRepository.save(comment);
    }
}
