package com.sparta.devmatebackend.models;

import com.fasterxml.jackson.annotation.*;
import com.sparta.devmatebackend.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
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

    public User(String username, String password, String name, Skill skill, String introduce, String imageUrl) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.skill = skill;
        this.introduce = introduce;
        this.imageUrl = imageUrl;
    }

    public User(UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getName();
        this.skill = userRequestDto.getSkill();
        this.introduce = userRequestDto.getIntroduce();
        this.imageUrl = userRequestDto.getImage_url();
    }

    public void update(UserRequestDto userRequestDto){
        this.name = userRequestDto.getName();
        this.skill = userRequestDto.getSkill();
        this.introduce = userRequestDto.getIntroduce();
        this.imageUrl = userRequestDto.getImage_url();
    }
}
