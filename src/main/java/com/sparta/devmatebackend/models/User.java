package com.sparta.devmatebackend.models;

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
}
