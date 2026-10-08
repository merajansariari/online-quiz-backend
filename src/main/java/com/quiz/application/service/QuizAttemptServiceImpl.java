package com.quiz.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quiz.application.dto.QuizResultDto;
import com.quiz.application.entity.AttemptAnswer;
import com.quiz.application.entity.Question;
import com.quiz.application.entity.QuizAttempt;
import com.quiz.application.exception.DuplicateAnswerException;
import com.quiz.application.exception.ResourceNotFoundException;
import com.quiz.application.repository.AttemptAnswerRepository;
import com.quiz.application.repository.QuestionRepository;
import com.quiz.application.repository.QuizAttemptRepository;

@Service 
public class QuizAttemptServiceImpl implements QuizAttemptService
{
	    private final QuizAttemptRepository quizAttemptRepository;
	    private final QuestionRepository questionRepository;
	    private final AttemptAnswerRepository attemptAnswerRepository;

	    public QuizAttemptServiceImpl(
	            QuizAttemptRepository quizAttemptRepository,
	            QuestionRepository questionRepository,
	            AttemptAnswerRepository attemptAnswerRepository) {

	        this.quizAttemptRepository = quizAttemptRepository;
	        this.questionRepository = questionRepository;
	        this.attemptAnswerRepository = attemptAnswerRepository;
	    }

	    @Override
	    public QuizAttempt createAttempt(QuizAttempt attempt) {

	        return quizAttemptRepository.save(attempt);
	    }

	    @Override
	    public QuizAttempt getAttemptById(Long id) {

	        return quizAttemptRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Quiz attempt not found with id: " + id
	                        )
	                );
	    }

	    @Override
	    public List<QuizAttempt> getAttemptsByUserId(Long userId) {

	        return quizAttemptRepository.findByUserId(userId);
	    }

	    @Override
	    public List<QuizAttempt> getAttemptsByQuizId(Long quizId) {

	        return quizAttemptRepository.findByQuizId(quizId);
	    }

	    @Override
	    public AttemptAnswer submitAnswer(
	            Long attemptId,
	            Long questionId,
	            String selectedAnswer) {

	        // 1. Find the attempt
	        QuizAttempt attempt =
	                quizAttemptRepository.findById(attemptId)
	                        .orElse(null);

	        // 2. Find the question
	        Question question =
	                questionRepository.findById(questionId)
	                        .orElse(null);

	        if (attempt == null || question == null) {
	            return null;
	        }

	        if (attempt.getQuiz().getId() != question.getQuiz().getId()) {
	            return null;
	        }
	        
	        
	        
	        Optional<AttemptAnswer> existingAnswer =
	                attemptAnswerRepository
	                        .findByQuizAttemptIdAndQuestionId(
	                                attemptId,
	                                questionId
	                        );

	        if (existingAnswer.isPresent()) {
	            throw new DuplicateAnswerException(
	                    "Answer already submitted for this question"
	            );
	        }
	        

	        // 3. Compare selected answer with correct answer
	        boolean isCorrect =
	                question.getCorrectAnswer()
	                        .equalsIgnoreCase(selectedAnswer);

	        // 4. Create AttemptAnswer
	        AttemptAnswer answer = new AttemptAnswer();

	        answer.setSelectAnswer(selectedAnswer);
	        answer.setCorrectAnswer(question.getCorrectAnswer());
	        answer.setCorrect(isCorrect);
	        answer.setQuizAttempt(attempt);
	        answer.setQuestion(question);

	        // 5. Save answer
	        AttemptAnswer savedAnswer =
	                attemptAnswerRepository.save(answer);

	        // 6. Increase score if correct
	        if (isCorrect) {

	            int currentScore =
	                    attempt.getScore() == null
	                            ? 0
	                            : attempt.getScore();

	            attempt.setScore(currentScore + 1);

	            quizAttemptRepository.save(attempt);
	        }

	        return savedAnswer;
	    }
	    
	    
	    
	    
	    @Override
	    public QuizResultDto getQuizResult(Long attemptId) {

	        QuizAttempt attempt =
	                quizAttemptRepository.findById(attemptId)
	                        .orElse(null);

	        if (attempt == null) {
	            return null;
	        }

	        int score =
	                attempt.getScore() == null
	                        ? 0
	                        : attempt.getScore();

	        int totalQuestions =
	                attempt.getTotalQuestions() == null
	                        ? 0
	                        : attempt.getTotalQuestions();

	        int incorrectAnswers =
	                totalQuestions - score;

	        QuizResultDto result = new QuizResultDto();

	        result.setAttemptId(attempt.getId());
	        result.setQuizId(attempt.getQuiz().getId());
	        result.setQuizTitle(attempt.getQuiz().getTitle());
	        result.setScore(score);
	        result.setTotalQuestions(totalQuestions);
	        result.setCorrectAnswers(score);
	        result.setIncorrectAnswers(incorrectAnswers);

	        return result;
	    }
	    
}
