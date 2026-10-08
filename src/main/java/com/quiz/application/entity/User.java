package com.quiz.application.entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User
{
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
	  
	  @Column(nullable = false, unique = true)
      private String username;
      
	  @Column(nullable = false, unique = true)
      private String email;
      
	  @Column(nullable = false )
      private String password;
      
	  @Column(nullable = false )
      private String role;
	  
	  @OneToMany(mappedBy = "user")
	  private List<QuizAttempt> attempts = new ArrayList<>();
}
