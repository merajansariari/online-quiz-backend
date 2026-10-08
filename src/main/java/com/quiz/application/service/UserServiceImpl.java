package com.quiz.application.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quiz.application.entity.User;
import com.quiz.application.exception.ResourceNotFoundException;
import com.quiz.application.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        // Check duplicate username
        Optional<User> existingUsername =
                userRepository.findByUsername(user.getUsername());

        if (existingUsername.isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists: " + user.getUsername()
            );
        }

        // Check duplicate email
        Optional<User> existingEmail =
                userRepository.findByEmail(user.getEmail());

        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException(
                    "Email already exists: " + user.getEmail()
            );
        }

        // Encode password before saving
        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    @Override
    public User getUserByUsername(String username) {

        Optional<User> user =
                userRepository.findByUsername(username);

        return user.orElse(null);
    }
}