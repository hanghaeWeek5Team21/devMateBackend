package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // create

    // check
    public boolean isLoginIdDuplicate(String loginId){
        List<User> userList = userRepository.findLoginIdDuplicateUsers(loginId);
        return !userList.isEmpty();
    }

    // update

    // delete
}
