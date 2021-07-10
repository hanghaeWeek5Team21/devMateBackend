package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.UserRequestDto;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // create
    @Transactional
    public void createUser(UserRequestDto userRequestDto){
        // 각 요소의 존재 확인
        if (userRequestDto.getUsername().equals("") || userRequestDto.getUsername() == null){
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
        List<User> userList = userRepository.findAllByUsername(userRequestDto.getUsername());
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
        List<User> userList = userRepository.findAllByUsername(loginId);
        return !userList.isEmpty();
    }

    // update
    @Transactional
    public void updateUser(UserDetailsImpl userDetails, UserRequestDto userRequestDto){
        if (userDetails == null){
            throw new IllegalArgumentException("회원이 로그인되어 있지 않습니다.");
        }
        User user = userDetails.getUser();
        user.update(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(user);
    }

    // delete
}
