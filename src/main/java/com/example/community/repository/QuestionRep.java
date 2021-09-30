package com.example.community.repository;

import com.example.community.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRep extends JpaRepository<Question, Long> {
}
