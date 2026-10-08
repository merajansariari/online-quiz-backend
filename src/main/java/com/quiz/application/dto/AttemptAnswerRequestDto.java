package com.quiz.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttemptAnswerRequestDto
{
	    @NotNull(message = "Question ID is required")
	    private Long questionId;

	    @NotBlank(message = "Selected answer is required")
	    private String selectedAnswer;
}
