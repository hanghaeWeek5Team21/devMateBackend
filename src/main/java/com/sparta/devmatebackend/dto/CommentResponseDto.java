package com.sparta.devmatebackend.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentResponseDto {
    private boolean res;
    private String msg;
}
