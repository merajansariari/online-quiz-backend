package com.quiz.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRequestDto
{
	  @NotBlank(message = "Quiz title is required")
	  @Size(max = 255, message = "Quiz title cannot exceed 255 characters")
      private String title;
	  
	  @Size(max = 1000, message = "Description cannot exceed 1000 characters")
      private String description;
	  
	  
	  @NotBlank(message = "Topic is required")
      private String topic;
	  
	  
	  @NotBlank(message = "Difficulty is required")
      private String difficulty;
}
