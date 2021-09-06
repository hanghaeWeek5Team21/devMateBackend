package com.sparta.devmatebackend.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentPutRequestDto {
    private String contents;
}
