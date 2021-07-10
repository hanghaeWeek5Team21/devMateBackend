package com.sparta.devmatebackend.repository;

import com.sparta.devmatebackend.models.Comment;
import com.sparta.devmatebackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUserOrderByCreatedAtDesc(User user);
}
