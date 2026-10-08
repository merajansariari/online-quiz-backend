package com.quiz.application.service;

import com.quiz.application.entity.AttemptAnswer;

public interface AttemptAnswerService  
{
	AttemptAnswer saveAnswer(AttemptAnswer answer);

    AttemptAnswer getAnswerById(Long id);
}
