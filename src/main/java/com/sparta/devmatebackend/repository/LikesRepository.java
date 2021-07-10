package com.sparta.devmatebackend.repository;

import com.sparta.devmatebackend.models.Likes;
import com.sparta.devmatebackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByAuthorAndUser(User author, User user);
}
