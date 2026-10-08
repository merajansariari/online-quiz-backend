package com.quiz.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDto 
{
	   @NotBlank(message = "Question text  is required")
	   @Size(max = 1000, message = "Question text cannot exceed 1000 characters") 
       private String questionText;
	   
	   
	   @NotBlank(message = "Option A is required")
       private String optionA;
	   
	   
	   @NotBlank(message = "Option B is required")
       private String optionB;
	   
	   
	   @NotBlank(message = "Option C is required")
       private String optionC;
	   
	   
	   @NotBlank(message = "Option D is required")
       private String optionD;
	   
	   
	   @NotBlank(message = "Correct answer is required")
       private String correctAnswer;
	   
	   
       private Long quizId;
       
}
