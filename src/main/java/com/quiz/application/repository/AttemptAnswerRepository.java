package com.quiz.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.application.entity.AttemptAnswer;

public interface AttemptAnswerRepository extends JpaRepository<AttemptAnswer, Long>
{
	 Optional<AttemptAnswer> findByQuizAttemptIdAndQuestionId(
	            Long attemptId,
	            Long questionId);
}
