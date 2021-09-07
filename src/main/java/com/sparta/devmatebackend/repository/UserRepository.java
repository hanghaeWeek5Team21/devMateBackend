package com.sparta.devmatebackend.repository;

import com.sparta.devmatebackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUsername(String username);
    List<User> findAllByOrderByModifiedAtDesc();
    Optional<User> findByUsername(String username);
}
