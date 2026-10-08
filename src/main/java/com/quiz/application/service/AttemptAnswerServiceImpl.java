package com.quiz.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quiz.application.entity.AttemptAnswer;
import com.quiz.application.repository.AttemptAnswerRepository;

@Service
public class AttemptAnswerServiceImpl implements AttemptAnswerService
{
       private final AttemptAnswerRepository attemptAnswerRepository;
       
       
	public AttemptAnswerServiceImpl(AttemptAnswerRepository attemptAnswerRepository)
	{
		this.attemptAnswerRepository = attemptAnswerRepository;
	}

	@Override
	public AttemptAnswer saveAnswer(AttemptAnswer answer)
	{
		return attemptAnswerRepository.save(answer);
	}

	@Override
	public AttemptAnswer getAnswerById(Long id)
	{
		Optional<AttemptAnswer> answer = attemptAnswerRepository.findById(id);
		
		return answer.orElse(null);
	}

}
