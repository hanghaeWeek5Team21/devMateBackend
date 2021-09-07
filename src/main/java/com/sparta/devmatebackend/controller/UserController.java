package com.sparta.devmatebackend.controller;
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
    public ResponseEntity<Long> getLoginId(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) return ResponseEntity.ok().body(null);
        return ResponseEntity.ok().body(userDetails.getUser().getId());
    }

    // 아이디 중복 체그용
    @PostMapping(value = "api/user", params = "login_id")
    public ResponseEntity<Void> checkLoginIdDuplicate(@RequestParam(name = "login_id") String loginId){
        if (userService.isLoginIdDuplicate(loginId)) return ResponseEntity.ok().build();
        return ResponseEntity.ok().build();
    }

    // 회원가입
    @PostMapping("api/user")
    public ResponseEntity<Void> createUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).build();
    }

    // 회원수정
    @PatchMapping("api/user")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserRequestDto userRequestDto){
        userService.updateUser(userDetails, userRequestDto);
        return ResponseEntity.ok().build();
    }

    // 회원 전체 조회
    @GetMapping("api/user")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userRepository.findAllByOrderByModifiedAtDesc();
        return ResponseEntity.ok().body(allUser);
    }

    // 회원 단일 조회
    @GetMapping("api/user/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable Long id){
        User user = userRepository.getById(id);
        return ResponseEntity.ok().body(user);
    }
}
