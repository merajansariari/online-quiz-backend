package com.quiz.application.service;

import java.util.List;

import com.quiz.application.entity.Question;

public interface QuestionService
{
	Question addQuestion(Question question);

    Question updateQuestion(Long id, Question question);

    void deleteQuestion(Long id);

    Question getQuestionById(Long id);

    List<Question> getQuestionsByQuizId(Long quizId);
}
