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


    //create
    @PostMapping("api/user")
    public ResponseEntity<Void> create(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        URI currentRequest = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(currentRequest).build();
    }

    //read
    @GetMapping("api/user/{id}")
    public ResponseEntity<User> read(@PathVariable Long id){
        User user = userRepository.getById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("api/user")
    public ResponseEntity<List<User>> readAll(){
        List<User> allUser = userRepository.findAllByOrderByModifiedAtDesc();
        return ResponseEntity.ok().body(allUser);
    }

    @GetMapping(value = "api/user/id")
    public ResponseEntity<Long> readId(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) return ResponseEntity.ok().body(null);
        return ResponseEntity.ok().body(userDetails.getUser().getId());
    }

    //check
    @PostMapping(value = "api/user/username", params = "username")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestParam(name = "username") String username){
        if (userService.isUsernameDuplicate(username)) return ResponseEntity.ok().body(true);
        return ResponseEntity.ok().body(false);
    }

    //update
    @PatchMapping("api/user")
    public ResponseEntity<Void> update(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody UserRequestDto userRequestDto){
        userService.updateUser(userDetails, userRequestDto);
        return ResponseEntity.ok().build();
    }
}
