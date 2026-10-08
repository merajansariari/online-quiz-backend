package com.quiz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class QuizResponseDto 
{
	private Long id;

    private String title;

    private String description;

    private String topic;

    private String difficulty;

    private Integer questionCount;
}
