package com.sparta.devmatebackend.dto.requestDto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentUpdateRequestDto {
    private String contents;
}
