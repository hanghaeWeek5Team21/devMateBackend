package com.sparta.devmatebackend.models;

import com.fasterxml.jackson.annotation.*;
import com.sparta.devmatebackend.dto.requestDto.UserRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Skill skill;
    private String introduce;
    private String imageUrl;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Comment> createdComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Likes> createdLikes = new ArrayList<>();

    public User(UserRequestDto userRequestDto, String password){
        this.username = userRequestDto.getUsername();
        this.name = userRequestDto.getName();
        this.skill = userRequestDto.getSkill();
        this.introduce = userRequestDto.getIntroduce();
        this.imageUrl = userRequestDto.getImage_url();
        this.password = password;
    }

    public void update(UserRequestDto userRequestDto, String password){
        this.name = userRequestDto.getName();
        this.skill = userRequestDto.getSkill();
        this.introduce = userRequestDto.getIntroduce();
        this.imageUrl = userRequestDto.getImage_url();
        this.password = password;
    }
}
