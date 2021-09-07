package com.sparta.devmatebackend.dto.requestDto;

import com.sparta.devmatebackend.models.Skill;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private String name;
    private Skill skill;
    private String introduce;
    private String image_url;
}

