package com.quiz.application.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiz_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt
{
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	 
     private Integer score;
     
     private Integer totalQuestions;
     
     private LocalDateTime attemtedAt;
     
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     private User user;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "quiz_id", nullable = false)
     private Quiz quiz;
     
     
     @OneToMany(
    	mappedBy = "quizAttempt",
    	cascade = CascadeType.ALL,
    	orphanRemoval = true
     )
     private List<AttemptAnswer> answers = new ArrayList<>();
}
