package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.ResMesResultResponseDto;
import com.sparta.devmatebackend.dto.UserRequestDto;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // create
    public void createUser(UserRequestDto userRequestDto){
        User user = new User(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(user);
    }

    // check
    public boolean isLoginIdDuplicate(String loginId){
        List<User> userList = userRepository.findLoginIdDuplicateUsers(loginId);
        return !userList.isEmpty();
    }

    // update

    // delete
}
