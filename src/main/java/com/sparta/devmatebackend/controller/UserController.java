package com.sparta.devmatebackend.controller;
import com.sparta.devmatebackend.dto.CommentResponseDto;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.dto.UserRequestDto;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 아이디 로그인 체그용
    @GetMapping(value = "api/user/id")
    public ResMesResultResponseDto getLoginId(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) return new ResMesResultResponseDto(false, "로그인되어 있지 않습니다.", null);
        return new ResMesResultResponseDto(true, "아이디 인덱스입니다.", userDetails.getUser().getId());
    }

    // 아이디 중복 체그용
    @PostMapping(value = "api/user", params = "login_id")
    public ResMesResultResponseDto checkLoginIdDuplicate(@RequestParam(name = "login_id") String loginId){
        boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
        String msg;
        if (isDuplicate) msg = "중복되는 아이디가 있습니다.";
        else msg = "중복되는 아이디가 없습니다.";
        return new ResMesResultResponseDto(isDuplicate, msg, null);
    }

    // 회원가입
    @PostMapping("api/user")
    public CommentResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        CommentResponseDto commentResponseDto;
        try{
            userService.createUser(userRequestDto);
            commentResponseDto = new CommentResponseDto(true, "회원가입이 완료되었습니다.");
        }catch (Exception e){
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    // 회원수정
    @PatchMapping("api/user")
    public CommentResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserRequestDto userRequestDto){
        CommentResponseDto commentResponseDto;
        try{
            userService.updateUser(userDetails, userRequestDto);
            commentResponseDto = new CommentResponseDto(true, "유저가 정상적으로 수정되었습니다.");
        }catch (Exception e){
            commentResponseDto = new CommentResponseDto(false, e.getMessage());
        }
        return commentResponseDto;
    }

    // 회원 전체 조회
    @GetMapping("api/user")
    public ResMesResultResponseDto getAllUser(){
        ResMesResultResponseDto resMesResultResponseDto;
        try{
            List<User> allUser = userRepository.findAllByOrderByModifiedAtDesc();
            resMesResultResponseDto = new ResMesResultResponseDto(true,"모든 user 가 정상적으로 조회되었습니다.", allUser);
        }catch (Exception e){
            resMesResultResponseDto = new ResMesResultResponseDto(false, e.getMessage(), null);
        }
        return resMesResultResponseDto;
    }

    // 회원 단일 조회
    @GetMapping("api/user/{id}")
    public ResMesResultResponseDto getSingleUser(@PathVariable Long id){
        ResMesResultResponseDto resMesResultResponseDto;
        try{
            User user = userRepository.getById(id);
            resMesResultResponseDto = new ResMesResultResponseDto(true,"단일 user 가 정상적으로 조회되었습니다.", user);
        }catch (Exception e){
            resMesResultResponseDto = new ResMesResultResponseDto(false, e.getMessage(), null);
        }
        return resMesResultResponseDto;
    }
}
