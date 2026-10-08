package com.quiz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.application.dto.QuizRequestDto;
import com.quiz.application.dto.QuizResponseDto;
import com.quiz.application.entity.Quiz;
import com.quiz.application.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController 
{
      private final QuizService quizService;

	  public QuizController(QuizService quizService)
	  {
		this.quizService = quizService;
	  }
      
	    @PostMapping
	    public ResponseEntity<QuizResponseDto> createQuiz(@Valid @RequestBody QuizRequestDto requestDto)
		{
		      Quiz quiz = new Quiz();
		      
		      quiz.setTitle(requestDto.getTitle());
		      quiz.setDescription(requestDto.getDescription());
		      quiz.setTopic(requestDto.getTopic());
		      quiz.setDifficulty(requestDto.getDifficulty());
		      
		      Quiz savedQuiz = quizService.createQuiz(quiz);
		      
		      
		      QuizResponseDto response = convertToResponseDto(savedQuiz);
		      
		      return ResponseEntity.status(HttpStatus.CREATED).body(response);
		 }
	    
	    
	    @GetMapping
	    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes()
	    {
	    	List<Quiz> quizzes = quizService.getAllQuizzes();
	    	
	    	
	    	List<QuizResponseDto> response = quizzes.stream()
	                .map(this::convertToResponseDto)
	                .toList();
	    	
	    	return ResponseEntity.ok(response);
	    }
	    
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<QuizResponseDto> getQuizById(
	            @PathVariable Long id) {

	        Quiz quiz = quizService.getQuizById(id);

	        return ResponseEntity.ok(
	                convertToResponseDto(quiz)
	        );
	    }
	    
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<QuizResponseDto> updateQuiz(
	            @PathVariable Long id,
	            @Valid @RequestBody QuizRequestDto requestDto) {

	        Quiz quiz = new Quiz();

	        quiz.setTitle(requestDto.getTitle());
	        quiz.setDescription(requestDto.getDescription());
	        quiz.setTopic(requestDto.getTopic());
	        quiz.setDifficulty(requestDto.getDifficulty());

	        Quiz updatedQuiz =
	                quizService.updateQuiz(id, quiz);

	        QuizResponseDto response =
	                convertToResponseDto(updatedQuiz);

	        return ResponseEntity.ok(response);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {

	        quizService.deleteQuiz(id);

	        return ResponseEntity.noContent().build();
	    }
	    
	    private QuizResponseDto convertToResponseDto(Quiz quiz)
	    {
	    	QuizResponseDto response = new QuizResponseDto();
	    	
	    	response.setId(quiz.getId());
	    	response.setTitle(quiz.getTitle());
	    	response.setDescription(quiz.getDescription());
	    	response.setTopic(quiz.getTopic());
	    	response.setDifficulty(quiz.getDifficulty());
	    	
	    	if(quiz.getQuestions() != null)
	    	{
	    		response.setQuestionCount(quiz.getQuestions().size());
	    	}
	    	else
	    	{
	    		response.setQuestionCount(0);
	    	}
	    	
	    	return  response;
	    }
	    
   }
      

