package com.sparta.devmatebackend.controller;
import com.sparta.devmatebackend.dto.responseDto.CommentResponseDto;
import com.sparta.devmatebackend.dto.responseDto.ResMesResultResponseDto;
import com.sparta.devmatebackend.dto.requestDto.UserRequestDto;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = {"${config.domain.full-name}"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 아이디 로그인 체그용
    @GetMapping(value = "api/user/id")
    public ResponseEntity<ResMesResultResponseDto> getLoginId(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) return ResponseEntity.ok().body(new ResMesResultResponseDto(false, "로그인되어 있지 않습니다.", null));
        return ResponseEntity.ok().body(new ResMesResultResponseDto(true, "아이디 인덱스입니다.", userDetails.getUser().getId()));
    }

    // 아이디 중복 체그용
    @PostMapping(value = "api/user", params = "login_id")
    public ResponseEntity<ResMesResultResponseDto> checkLoginIdDuplicate(@RequestParam(name = "login_id") String loginId){
        if (userService.isLoginIdDuplicate(loginId)) return ResponseEntity.ok().body(new ResMesResultResponseDto(true, "중복되는 아이디가 있습니다.", null));
        return ResponseEntity.ok().body(new ResMesResultResponseDto(false, "중복되는 아이디가 없습니다.", null));
    }

    // 회원가입
    @PostMapping("api/user")
    public ResponseEntity<CommentResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).body(new CommentResponseDto(true, "회원가입이 완료되었습니다."));
    }

    // 회원수정
    @PatchMapping("api/user")
    public ResponseEntity<CommentResponseDto> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserRequestDto userRequestDto){
        userService.updateUser(userDetails, userRequestDto);
        return ResponseEntity.ok().body(new CommentResponseDto(true, "유저가 정상적으로 수정되었습니다."));
    }

    // 회원 전체 조회
    @GetMapping("api/user")
    public ResponseEntity<ResMesResultResponseDto> getAllUser(){
        List<User> allUser = userRepository.findAllByOrderByModifiedAtDesc();
        return ResponseEntity.ok().body(new ResMesResultResponseDto(true,"모든 user 가 정상적으로 조회되었습니다.", allUser));
    }

    // 회원 단일 조회
    @GetMapping("api/user/{id}")
    public ResponseEntity<ResMesResultResponseDto> getSingleUser(@PathVariable Long id){
        User user = userRepository.getById(id);
        return ResponseEntity.ok().body(new ResMesResultResponseDto(true,"단일 user 가 정상적으로 조회되었습니다.", user));
    }
}
