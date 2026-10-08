package com.quiz.application.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
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
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question
{
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
	   
	   @Column(nullable = false, length = 1000)
       private String questionText;
	   
	   @Column(nullable = false)
       private String optionA;
	   
	   @Column(nullable = false)
       private String optionB;
       
	   @Column(nullable = false)
       private String optionC;
       
	   @Column(nullable = false)
       private String optionD;
       
	   @Column(nullable = false)
       private String correctAnswer;
       
	   
	   @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "quiz_id", nullable = false)
	   private Quiz quiz;
	   
	   @OneToMany(mappedBy = "question")
	   private List<AttemptAnswer> attemptAnswers = new ArrayList<>();
}
