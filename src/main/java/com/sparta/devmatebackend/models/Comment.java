package com.sparta.devmatebackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.devmatebackend.dto.requestDto.CommentUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Comment(String contents, User user, User author) {
        this.contents = contents;
        this.user = user;
        this.author = author;
    }

    public void update(CommentUpdateRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
}
