package com.quiz.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attempt_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttemptAnswer
{
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY  )
     private Long id;
	 
	 
     private String selectAnswer;
     
     
     private String correctAnswer;
     
     
     private Boolean correct;
     
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "attempt_id", nullable = false)
     private QuizAttempt quizAttempt;
     
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "question_id", nullable = false)
     private Question question; 
}
