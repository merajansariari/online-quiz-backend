package com.quiz.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttemptResponseDto
{
	 private Long id;

	    private Long userId;

	    private Long quizId;

	    private String quizTitle;

	    private Integer score;

	    private Integer totalQuestions;

	    private LocalDateTime attemptedAt;
}
