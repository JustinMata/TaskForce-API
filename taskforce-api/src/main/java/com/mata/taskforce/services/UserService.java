package com.mata.taskforce.services;

import com.mata.taskforce.domain.User;
import com.mata.taskforce.exceptions.TfAuthException;

public interface UserService {
	User validateUser(String email, String password) throws TfAuthException;
	
	User registerUser(String firstName, String lastName, String email, String password) throws TfAuthException;
}
