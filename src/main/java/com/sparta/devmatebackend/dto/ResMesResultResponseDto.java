package com.sparta.devmatebackend.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ResMesResultResponseDto {
    private boolean res;
    private String msg;
    private Object result;
}
