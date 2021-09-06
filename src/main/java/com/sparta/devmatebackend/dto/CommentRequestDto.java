package com.sparta.devmatebackend.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentRequestDto {
    private Long user_id;
    private String contents;
}
