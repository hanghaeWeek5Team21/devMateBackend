package com.sparta.devmatebackend.service;

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
        // 각 요소의 존재 확인
        if (userRequestDto.getLogin_id().equals("") || userRequestDto.getLogin_id() == null){
            throw new IllegalArgumentException("아이디가 입력되지 않았습니다.");
        }
        if (userRequestDto.getPassword().equals("") || userRequestDto.getPassword() == null){
            throw new IllegalArgumentException("비밀번호가 입력되지 않았습니다.");
        }
        if (userRequestDto.getImage_url().equals("") || userRequestDto.getImage_url() == null){
            throw new IllegalArgumentException("이미지가 입력되지 않았습니다.");
        }
        if (userRequestDto.getIntroduce().equals("") || userRequestDto.getIntroduce() == null){
            throw new IllegalArgumentException("자기소개가 입력되지 않았습니다.");
        }
        if (userRequestDto.getName().equals("") || userRequestDto.getName() == null){
            throw new IllegalArgumentException("이름이 입력되지 않았습니다.");
        }
        if (userRequestDto.getSkill() == null){
            throw new IllegalArgumentException("스킬이 입력되지 않았습니다.");
        }
        // 아이디 중복 확인
        List<User> userList = userRepository.findLoginIdDuplicateUsers(userRequestDto.getLogin_id());
        if (!userList.isEmpty()){
            throw new IllegalArgumentException("중복되는 아이디가 존재합니다.");
        }
        // 회원 저장
        User user = new User(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(user);
    }

    // read
    public boolean isLoginIdDuplicate(String loginId){
        // 아이디 중복 확인에 따른 bool
        List<User> userList = userRepository.findLoginIdDuplicateUsers(loginId);
        return !userList.isEmpty();
    }

    // update

    // delete
}
