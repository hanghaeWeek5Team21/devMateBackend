package com.sparta.devmatebackend.models;


import com.sparta.devmatebackend.dto.CommentPutRequestDto;
import com.sparta.devmatebackend.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Long author_id;

    public Comment(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.user_id = requestDto.getUser_id();
    }

    public void update(CommentPutRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
}
