package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class TestController {

    @GetMapping("/api/test")
    public ResMesResultResponseDto credentialCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("userDetails = " + userDetails);
        return new ResMesResultResponseDto();
    }

    @PostMapping("/api/test")
    public ResMesResultResponseDto credentialCheck2(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody CommentRequestDto commentRequestDto){
        System.out.println("userDetails = " + userDetails);
        System.out.println("commentRequestDto = " + commentRequestDto.getUser_id());
        System.out.println("commentRequestDto = " + commentRequestDto.getContents());
        return new ResMesResultResponseDto();
    }

    @PatchMapping("/api/test")
    public ResMesResultResponseDto credentialCheck3(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto){
        System.out.println("userDetails = " + userDetails);
        System.out.println("commentRequestDto = " + commentRequestDto.getUser_id());
        System.out.println("commentRequestDto = " + commentRequestDto.getContents());
        return new ResMesResultResponseDto();
    }

    @DeleteMapping("/api/test")
    public ResMesResultResponseDto credentialCheck4(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("userDetails = " + userDetails);
        return new ResMesResultResponseDto();
    }
}
