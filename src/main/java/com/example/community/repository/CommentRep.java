package com.example.community.repository;

import com.example.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRep extends JpaRepository<Comment, Long> {
    @Query(value = "Select * From Comment AS c Where c.parent_id = ?1 and c.type=1", nativeQuery = true)
    List<Comment> findCommentsByParent_idType(Long id);
}
