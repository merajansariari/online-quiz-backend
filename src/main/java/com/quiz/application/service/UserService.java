package com.quiz.application.service;

import com.quiz.application.entity.User;

public interface UserService
{
	User registerUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);
}
