package com.sparta.devmatebackend.controller;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.dto.UserRequestDto;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import com.sparta.devmatebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
    public ResMesResultResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        resDto.setResult(null);
        try{
            userService.createUser(userRequestDto);
            resDto.setRes(true);
            resDto.setMsg("회원가입이 완료되었습니다.");
        }catch (Exception e){
            resDto.setRes(false);
            resDto.setMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return resDto;
    }

    // 회원수정
    @PatchMapping("api/user")
    public ResMesResultResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserRequestDto userRequestDto){
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        resDto.setResult(null);
        try{
            userService.updateUser(userDetails, userRequestDto);
            resDto.setRes(true);
            resDto.setMsg("유저가 정상적으로 수정되었습니다.");
        }catch (Exception e){
            resDto.setRes(false);
            resDto.setMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return resDto;
    }

    // 회원 전체 조회
    @GetMapping("api/user")
    public ResMesResultResponseDto getAllUser(){
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        try{
            List<User> allUser = userRepository.findAllByOrderByModifiedAtDesc();
            resDto.setRes(true);
            resDto.setResult(allUser);
            resDto.setMsg("모든 user 가 정상적으로 조회되었습니다.");
        }catch (Exception e){
            resDto.setRes(false);
            resDto.setResult(null);
            resDto.setMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return resDto;
    }

    // 회원 단일 조회
    @GetMapping("api/user/{id}")
    public ResMesResultResponseDto getAllUser(@PathVariable Long id){
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        try{
            User allUser = userRepository.getById(id);
            resDto.setRes(true);
            resDto.setResult(allUser);
            System.out.println("allUser = " + allUser);
            resDto.setMsg("단일 user 가 정상적으로 조회되었습니다.");
        }catch (Exception e){
            resDto.setRes(false);
            resDto.setResult(null);
            resDto.setMsg(e.getMessage());
            System.out.println(e.getMessage());
        }
        return resDto;
    }
}
