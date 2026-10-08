package com.quiz.application.service;

import java.util.List;

import com.quiz.application.dto.QuizResultDto;
import com.quiz.application.entity.AttemptAnswer;
import com.quiz.application.entity.QuizAttempt;

public interface QuizAttemptService
{
	QuizAttempt createAttempt(QuizAttempt attempt);

    QuizAttempt getAttemptById(Long id);

    List<QuizAttempt> getAttemptsByUserId(Long userId);

    List<QuizAttempt> getAttemptsByQuizId(Long quizId);
    
    AttemptAnswer submitAnswer(Long attemptId, Long questionId, String selectedAnswer);
    
    QuizResultDto getQuizResult(Long attemptId);

}
