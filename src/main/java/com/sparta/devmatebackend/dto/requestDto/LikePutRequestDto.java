package com.sparta.devmatebackend.dto.requestDto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LikePutRequestDto {
    private Long user_id;
}
