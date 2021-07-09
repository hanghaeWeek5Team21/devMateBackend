package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.CommentRequestDto;
import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.dto.UserRequestDto;
import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.DELETE)
    public ResMesResultResponseDto checkLoginIdDuplicate(@RequestParam(name = "login_id") String loginId){
        boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
        String msg = "";
        if (isDuplicate) msg = "중복되는 아이디가 있습니다.";
        else msg = "중복되는 아이디가 없습니다.";
        return new ResMesResultResponseDto(isDuplicate, msg, null);
    }

    @PostMapping("api/user")
    public ResMesResultResponseDto create(@RequestBody UserRequestDto userRequestDto){
        ResMesResultResponseDto resDto = new ResMesResultResponseDto();
        resDto.setResult(null);
        try{
            userService.createUser(userRequestDto);
            resDto.setRes(true);
            resDto.setMsg("회원가입이 완료되었습니다.");
        }catch (Exception e){
            resDto.setRes(false);
            resDto.setMsg(e.getMessage());
        }
        return resDto;
    }
}
