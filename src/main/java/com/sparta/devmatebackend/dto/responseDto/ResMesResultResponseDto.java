package com.sparta.devmatebackend.dto.responseDto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ResMesResultResponseDto {
    private boolean res;
    private String msg;
    private Object result;
}
