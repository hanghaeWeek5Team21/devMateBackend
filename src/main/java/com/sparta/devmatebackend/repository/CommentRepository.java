package com.sparta.devmatebackend.repository;

import com.sparta.devmatebackend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM comment WHERE user_id = :user_id order by created_at desc",nativeQuery = true)
    List<Comment> findByUserIdOrderByCreatedAtDesc(@Param(value="user_id")Long user_id);
}
