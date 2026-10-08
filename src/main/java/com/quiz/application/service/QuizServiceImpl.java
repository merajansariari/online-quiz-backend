package com.quiz.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quiz.application.entity.Quiz;
import com.quiz.application.exception.ResourceNotFoundException;
import com.quiz.application.repository.QuizRepository;
import com.quiz.application.repository.UserRepository;

@Service
public class QuizServiceImpl implements QuizService
{
       
		private final UserRepository userRepository;
		private  final QuizRepository quizRepository;
	    
	public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository)
	{
	        this.quizRepository = quizRepository;
			this.userRepository = userRepository;
	}
	
	
	@Override
	public Quiz createQuiz(Quiz quiz)
	{
		return quizRepository.save(quiz);
	}

	@Override
	public Quiz updateQuiz(Long id, Quiz quiz) {

	    Quiz existing =
	            quizRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Quiz not found with id: " + id
	                            )
	                    );

	    existing.setTitle(quiz.getTitle());
	    existing.setDescription(quiz.getDescription());
	    existing.setTopic(quiz.getTopic());
	    existing.setDifficulty(quiz.getDifficulty());

	    return quizRepository.save(existing);
	}
	
	@Override
	public void deleteQuiz(Long id) {

	    Quiz quiz =
	            quizRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Quiz not found with id: " + id
	                            )
	                    );

	    quizRepository.delete(quiz);
	}

	@Override
	public Quiz getQuizById(Long id) {

	    return quizRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Quiz not found with id: " + id
	                    )
	            );
	}

	@Override
	public List<Quiz> getAllQuizzes() 
	{
		return quizRepository.findAll();
	}

}
