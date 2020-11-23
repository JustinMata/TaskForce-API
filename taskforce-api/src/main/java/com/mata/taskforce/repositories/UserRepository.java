package com.mata.taskforce.repositories;

import com.mata.taskforce.domain.User;
import com.mata.taskforce.exceptions.TfAuthException;

public interface UserRepository {
	Integer create(String firstName, String lastName, String email, String password) throws TfAuthException;
	
	User findByEmailAndPassword(String email, String password) throws TfAuthException;
	
	Boolean doesEmailExist(String email);
	
	User findById(Integer userId);
}
