package com.sparta.devmatebackend.service;

import com.sparta.devmatebackend.dto.requestDto.LikePutRequestDto;
import com.sparta.devmatebackend.models.Likes;
import com.sparta.devmatebackend.models.User;
import com.sparta.devmatebackend.repository.LikesRepository;
import com.sparta.devmatebackend.repository.UserRepository;
import com.sparta.devmatebackend.security.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(LikePutRequestDto likePutRequestDto, UserDetailsImpl userDetails) {
        User user = userRepository.getById(likePutRequestDto.getUser_id());
        User author = userDetails.getUser();
        System.out.println("author = " + author.getId());
        System.out.println("user = " + user.getId());
        likesRepository.findByAuthorAndUser(author, user)
            .ifPresent(x -> {throw new RuntimeException("같은 회원을 2번 좋아요 할 수 없습니다.");});

        Likes like = new Likes(user, author);
        likesRepository.save(like);
    }

    @Transactional
    public void delete(Long id, UserDetailsImpl userDetails)throws IllegalAccessException {
        Likes likes = likesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 좋아요입니다.")
        );
        if (!likes.getAuthor().getId().equals(userDetails.getUser().getId())){
            throw new IllegalAccessException("로그인된 유저가 작성한 좋아요가 아닙니다.");
        }
        likesRepository.delete(likes);
    }
}
