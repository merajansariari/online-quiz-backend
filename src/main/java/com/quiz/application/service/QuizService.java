package com.quiz.application.service;

import java.util.List;

import com.quiz.application.entity.Quiz;

public interface QuizService 
{
	Quiz createQuiz(Quiz quiz);

    Quiz updateQuiz(Long id, Quiz quiz);

    void deleteQuiz(Long id);

    Quiz getQuizById(Long id);

    List<Quiz> getAllQuizzes();
}
