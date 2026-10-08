package com.quiz.application.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.action.internal.OrphanRemovalAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz
{
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	 
	 @Column(nullable = false)
     private String title;
	 
	 @Column(length = 1000)
     private String description;
	 
	 @Column(nullable = false)
     private String topic;
	 
	 @Column(nullable = false)
     private String difficulty;
	 
	 
	 @OneToMany(
		mappedBy = "quiz",
		cascade = CascadeType.ALL,
	    orphanRemoval = true
	    )
     private List<Question> questions = new ArrayList<>();
}
