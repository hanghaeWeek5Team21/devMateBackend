package com.sparta.devmatebackend.repository;

import com.sparta.devmatebackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.loginId = :loginId")
    List<User> findLoginIdDuplicateUsers(String loginId);

    Optional<User> findByLoginId(String loginId);

    List<User> findAllByOrderByModifiedAtDesc();
}
