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
    private String username;

    public Comment(CommentRequestDto requestDto){
        this.id = requestDto.getId();
        this.contents = requestDto.getContents();
        this.user_id = requestDto.getUser_id();
        this.username = requestDto.getUsername();
    }

    public void update(CommentPutRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
}
