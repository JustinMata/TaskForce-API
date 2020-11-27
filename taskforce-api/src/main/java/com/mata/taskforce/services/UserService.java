package com.mata.taskforce.services;

import com.mata.taskforce.model.User;

public interface UserService {
	User validateUser(String email, String password);
	
	User registerUser(User user);
}