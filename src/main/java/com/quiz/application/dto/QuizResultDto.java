package com.quiz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResultDto 
{
	private Long attemptId;

    private Long quizId;

    private String quizTitle;

    private Integer score;

    private Integer totalQuestions;

    private Integer correctAnswers;

    private Integer incorrectAnswers;
}
