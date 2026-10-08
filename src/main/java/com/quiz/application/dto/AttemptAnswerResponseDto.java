package com.quiz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttemptAnswerResponseDto 
{
	private Long questionId;
    private String selectedAnswer;
    private Boolean correct;
}
