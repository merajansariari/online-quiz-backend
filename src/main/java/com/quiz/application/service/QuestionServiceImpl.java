package com.quiz.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quiz.application.entity.Question;
import com.quiz.application.exception.ResourceNotFoundException;
import com.quiz.application.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService
{
           
	   private final QuestionRepository questionRepository;
	   
	 public QuestionServiceImpl(QuestionRepository questionRepository)
	 {
	        this.questionRepository = questionRepository;
	 }
	
	@Override
	public Question addQuestion(Question question)
	{
		return questionRepository.save(question);
	}

	@Override
	public Question updateQuestion(Long id, Question question) {

	    Question existing =
	            questionRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Question not found with id: " + id
	                            )
	                    );

	    existing.setQuestionText(question.getQuestionText());
	    existing.setOptionA(question.getOptionA());
	    existing.setOptionB(question.getOptionB());
	    existing.setOptionC(question.getOptionC());
	    existing.setOptionD(question.getOptionD());
	    existing.setCorrectAnswer(question.getCorrectAnswer());

	    return questionRepository.save(existing);
	}

	@Override
	public void deleteQuestion(Long id) {

	    Question question =
	            questionRepository.findById(id)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Question not found with id: " + id
	                            )
	                    );

	    questionRepository.delete(question);
	}

	@Override
	public Question getQuestionById(Long id) {

	    return questionRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Question not found with id: " + id
	                    )
	            );
	}

	@Override
	public List<Question> getQuestionsByQuizId(Long quizId) 
	{
		return questionRepository.findByQuizId(quizId);
	}

}
