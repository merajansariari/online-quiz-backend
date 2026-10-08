package com.quiz.application.exception;

public class DuplicateAnswerException extends RuntimeException
{
	public DuplicateAnswerException(String message) {
        super(message);
    }
}
