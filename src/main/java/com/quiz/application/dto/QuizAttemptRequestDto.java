package com.quiz.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAttemptRequestDto
{
	@NotNull(message = "Quiz ID is required")
    private Long quizId;
}
