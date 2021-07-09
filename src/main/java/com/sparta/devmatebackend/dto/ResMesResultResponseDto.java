package com.sparta.devmatebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResMesResultResponseDto {
    private boolean res;
    private String msg;
    private Object result;
}
