package com.quiz.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.application.entity.QuizAttempt;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long>
{

    List<QuizAttempt> findByUserId(Long userId);

    List<QuizAttempt> findByQuizId(Long quizId);
}
