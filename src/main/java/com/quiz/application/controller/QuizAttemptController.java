package com.quiz.application.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.quiz.application.dto.AttemptAnswerRequestDto;
import com.quiz.application.dto.AttemptAnswerResponseDto;
import com.quiz.application.dto.AttemptResponseDto;
import com.quiz.application.dto.QuizAttemptRequestDto;
import com.quiz.application.dto.QuizResultDto;
import com.quiz.application.entity.AttemptAnswer;
import com.quiz.application.entity.Quiz;
import com.quiz.application.entity.QuizAttempt;
import com.quiz.application.entity.User;
import com.quiz.application.service.QuizAttemptService;
import com.quiz.application.service.QuizService;
import com.quiz.application.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attempts")
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;
    private final UserService userService;
    private final QuizService quizService;

    public QuizAttemptController(
            QuizAttemptService quizAttemptService,
            UserService userService,
            QuizService quizService) {

        this.quizAttemptService = quizAttemptService;
        this.userService = userService;
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<AttemptResponseDto> createAttempt(
            @Valid @RequestBody QuizAttemptRequestDto requestDto,
            Authentication authentication) {

        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Quiz quiz = quizService.getQuizById(requestDto.getQuizId());

        QuizAttempt attempt = new QuizAttempt();

        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setAttemtedAt(LocalDateTime.now());
        attempt.setScore(0);
        attempt.setTotalQuestions(quiz.getQuestions().size());

        QuizAttempt savedAttempt =
                quizAttemptService.createAttempt(attempt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToResponseDto(savedAttempt));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttemptResponseDto> getAttemptById(
            @PathVariable Long id,
            Authentication authentication) {

        QuizAttempt attempt =
                quizAttemptService.getAttemptById(id);

        String username = authentication.getName();

        User loggedInUser =
                userService.getUserByUsername(username);

        if (loggedInUser == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_ADMIN"));

        if (!isAdmin &&
                attempt.getUser().getId() != loggedInUser.getId()) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                convertToResponseDto(attempt)
        );
    }

    @GetMapping("/user")
    public ResponseEntity<List<AttemptResponseDto>> getMyAttempts(
            Authentication authentication) {

        String username = authentication.getName();

        User user =
                userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<AttemptResponseDto> response =
                quizAttemptService
                        .getAttemptsByUserId(user.getId())
                        .stream()
                        .map(this::convertToResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<AttemptResponseDto>> getAttemptsByQuiz(
            @PathVariable Long quizId) {

        List<AttemptResponseDto> response =
                quizAttemptService
                        .getAttemptsByQuizId(quizId)
                        .stream()
                        .map(this::convertToResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attemptId}/answers")
    public ResponseEntity<AttemptAnswerResponseDto> submitAnswer(
            @PathVariable Long attemptId,
            @Valid @RequestBody AttemptAnswerRequestDto request,
            Authentication authentication) {

        // Get the attempt first
        QuizAttempt attempt =
                quizAttemptService.getAttemptById(attemptId);

        // Get currently logged-in user
        String username = authentication.getName();

        User loggedInUser =
                userService.getUserByUsername(username);

        if (loggedInUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Check whether logged-in user is ADMIN
        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_ADMIN"));

        // USER can answer only his/her own attempt.
        // ADMIN can answer any attempt.
        if (!isAdmin &&
                attempt.getUser().getId() != loggedInUser.getId()) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AttemptAnswer answer =
                quizAttemptService.submitAnswer(
                        attemptId,
                        request.getQuestionId(),
                        request.getSelectedAnswer()
                );

        if (answer == null) {
            return ResponseEntity.notFound().build();
        }

        AttemptAnswerResponseDto response =
                new AttemptAnswerResponseDto();

        response.setQuestionId(
                answer.getQuestion().getId()
        );

        response.setSelectedAnswer(
                answer.getSelectAnswer()
        );

        response.setCorrect(
                answer.getCorrect()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{attemptId}/result")
    public ResponseEntity<QuizResultDto> getQuizResult(
            @PathVariable Long attemptId,
            Authentication authentication) {

        QuizAttempt attempt =
                quizAttemptService.getAttemptById(attemptId);

        String username =
                authentication.getName();

        User loggedInUser =
                userService.getUserByUsername(username);

        if (loggedInUser == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_ADMIN"));

        if (!isAdmin &&
                attempt.getUser().getId() != loggedInUser.getId()) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        QuizResultDto result =
                quizAttemptService.getQuizResult(attemptId);

        return ResponseEntity.ok(result);
    }

    private AttemptResponseDto convertToResponseDto(
            QuizAttempt attempt) {

        AttemptResponseDto response =
                new AttemptResponseDto();

        response.setId(attempt.getId());

        if (attempt.getUser() != null) {
            response.setUserId(
                    attempt.getUser().getId()
            );
        }

        if (attempt.getQuiz() != null) {
            response.setQuizId(
                    attempt.getQuiz().getId()
            );

            response.setQuizTitle(
                    attempt.getQuiz().getTitle()
            );
        }

        response.setScore(
                attempt.getScore()
        );

        response.setTotalQuestions(
                attempt.getTotalQuestions()
        );

        response.setAttemptedAt(
                attempt.getAttemtedAt()
        );

        return response;
    }
}