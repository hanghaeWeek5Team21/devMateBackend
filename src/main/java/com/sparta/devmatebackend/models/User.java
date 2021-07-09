package com.sparta.devmatebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.devmatebackend.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    @JsonIgnore
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Skill skill;
    private String introduce;
    private String imageUrl;

    public User(String loginId, String password, String name, Skill skill, String introduce, String imageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.skill = skill;
        this.introduce = introduce;
        this.imageUrl = imageUrl;
    }

    public User(UserRequestDto userRequestDto){
        this.loginId = userRequestDto.getLogin_id();
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getName();
        this.skill = userRequestDto.getSkill();
        this.introduce = userRequestDto.getIntroduce();
        this.imageUrl = userRequestDto.getImage_url();
    }
}
