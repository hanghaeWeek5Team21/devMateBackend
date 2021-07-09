package com.sparta.devmatebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ResMesResultResponseDto {
    private boolean res;
    private String msg;
    private Object result;
}
