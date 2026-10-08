package com.quiz.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.application.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>
{

}
