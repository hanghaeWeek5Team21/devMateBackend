package com.sparta.devmatebackend.controller;

import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.DELETE)
    public ResMesResultResponseDto checkLoginIdDuplicate(@RequestParam(name = "login_id") String loginId){
        boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
        String msg = "";
        if (isDuplicate) msg = "중복되는 아이디가 있습니다.";
        else msg = "중복되는 아이디가 없습니다.";
        return new ResMesResultResponseDto(isDuplicate, msg, null);
    }
}
