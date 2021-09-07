package com.sparta.devmatebackend.dto.requestDto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentCreateRequestDto {
    private Long user_id;
    private String contents;
}
