package com.quiz.application.controller;

import com.quiz.application.dto.UserRegistrationDto;
import com.quiz.application.dto.UserResponseDto;
import com.quiz.application.entity.User;
import com.quiz.application.service.UserService;

import jakarta.validation.Valid;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @Valid @RequestBody UserRegistrationDto registrationDto) {

        User user = new User();

        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setRole("USER");

        User savedUser = userService.registerUser(user);

        UserResponseDto response = new UserResponseDto();

        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(Principal principal) {

        User user = userService.getUserByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDto response = new UserResponseDto();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return ResponseEntity.ok(response);
    }
}