package com.quiz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDto
{
	private Long id;

    private String questionText;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private Long quizId;    
}
