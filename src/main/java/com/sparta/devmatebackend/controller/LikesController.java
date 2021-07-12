package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentResponseDto;
import com.sparta.devmatebackend.dto.LikePutRequestDto;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.repository.LikesRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://www.adiy.info"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesRepository likesRepository;
    private final LikesService likesService;

    @PostMapping("api/likes")
    public ResMesResultResponseDto create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LikePutRequestDto likePutRequestDto) {
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        try {
            likesService.create(likePutRequestDto, userDetails);
            resDto.setRes(true);
            resDto.setMsg("좋아요가 작성되었습니다.");
        } catch (Exception e) {
            resDto.setRes(false);
            resDto.setMsg(e.getMessage());
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return resDto;
    }

    @DeleteMapping("api/likes/{id}")
    public ResMesResultResponseDto delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        ResMesResultResponseDto respDto = new ResMesResultResponseDto();
        try {
            likesService.delete(id, userDetails);
            respDto.setRes(true);
            respDto.setMsg("좋아요가 삭제되었습니다.");
        } catch (Exception e) {
            respDto.setRes(false);
            respDto.setMsg(e.getMessage());
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return respDto;
    }
}
