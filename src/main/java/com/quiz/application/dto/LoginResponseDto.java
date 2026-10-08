package com.quiz.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto
{
	 private String token;

	    public LoginResponseDto(String token) {
	        this.token = token;
	    }
}
