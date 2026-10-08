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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.application.dto.QuestionRequestDto;
import com.quiz.application.dto.QuestionResponseDto;
import com.quiz.application.entity.Question;
import com.quiz.application.entity.Quiz;
import com.quiz.application.service.QuestionService;
import com.quiz.application.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController 
{
       private final QuestionService questionService;
       private final QuizService quizService;

	   public QuestionController(QuestionService questionService, QuizService quizService) 
	   {
		this.questionService = questionService;
		this.quizService = quizService;
	   }
	   
	   
	   @PostMapping
	   public ResponseEntity<QuestionResponseDto> addQuestion(
	           @Valid @RequestBody QuestionRequestDto requestDto) {

	       Quiz quiz =
	               quizService.getQuizById(requestDto.getQuizId());

	       Question question = new Question();

	       question.setQuestionText(requestDto.getQuestionText());
	       question.setOptionA(requestDto.getOptionA());
	       question.setOptionB(requestDto.getOptionB());
	       question.setOptionC(requestDto.getOptionC());
	       question.setOptionD(requestDto.getOptionD());
	       question.setCorrectAnswer(requestDto.getCorrectAnswer());
	       question.setQuiz(quiz);

	       Question savedQuestion =
	               questionService.addQuestion(question);

	       QuestionResponseDto response =
	               convertToResponseDto(savedQuestion);

	       return ResponseEntity
	               .status(HttpStatus.CREATED)
	               .body(response);
	   }
	   
	   
	   @GetMapping("/{id}")
	   public ResponseEntity<QuestionResponseDto> getQuestionById(
	           @PathVariable Long id) {

	       Question question =
	               questionService.getQuestionById(id);

	       return ResponseEntity.ok(
	               convertToResponseDto(question)
	       );
	   }
       
	   
	   
	   @GetMapping("/quiz/{quizId}")
	   public ResponseEntity<List<QuestionResponseDto>> getQuestionsByQuiz(@PathVariable Long quizId)
	   {
                		   List<Question> questions = questionService.getQuestionsByQuizId(quizId);
                		   
                		   List<QuestionResponseDto> response = questions.stream()
                		    .map(this::convertToResponseDto)
                		    .toList(); 
                		   
                		   return ResponseEntity.ok(response);
	   }
	   
	   
	   
	   @PutMapping("/{id}")
	   public ResponseEntity<QuestionResponseDto> updateQuestion(
	           @PathVariable Long id,
	           @Valid @RequestBody QuestionRequestDto requestDto) {

	       Question question = new Question();

	       question.setQuestionText(requestDto.getQuestionText());
	       question.setOptionA(requestDto.getOptionA());
	       question.setOptionB(requestDto.getOptionB());
	       question.setOptionC(requestDto.getOptionC());
	       question.setOptionD(requestDto.getOptionD());
	       question.setCorrectAnswer(requestDto.getCorrectAnswer());

	       Question updatedQuestion =
	               questionService.updateQuestion(id, question);

	       QuestionResponseDto response =
	               convertToResponseDto(updatedQuestion);

	       return ResponseEntity.ok(response);
	   }
	   
	   
	   @DeleteMapping("/{id}")
	   public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {

	       questionService.deleteQuestion(id);

	       return ResponseEntity.noContent().build();
	   }
	   
	   
	   
	   private QuestionResponseDto convertToResponseDto(Question question)
	   {
		   QuestionResponseDto response = new QuestionResponseDto();
		   
		   response.setId(question.getId());
		   response.setQuestionText(question.getQuestionText());
		   response.setOptionA(question.getOptionA());
		   response.setOptionB(question.getOptionB());
		   response.setOptionC(question.getOptionC());
		   response.setOptionD(question.getOptionD());
		   
		   if(question.getQuiz() != null)
		   {
			   response.setQuizId(question.getQuiz().getId());
		   }
		   
		   
		   return response;
	   }
	   
}
