package com.sparta.devmatebackend.dto;

import com.sparta.devmatebackend.models.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserRequestDto {
    private String login_id;
    private String password;
    private String name;
    private Skill skill;
    private String introduce;
    private String image_url;
}