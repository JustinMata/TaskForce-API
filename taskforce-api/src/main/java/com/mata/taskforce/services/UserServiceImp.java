package com.mata.taskforce.services;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mata.taskforce.domain.User;
import com.mata.taskforce.exceptions.TfAuthException;
import com.mata.taskforce.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImp implements UserService {
	@Autowired
	UserRepository userRepo;

	@Override
	public User validateUser(String email, String password) throws TfAuthException {
		if (email != null) email = email.toLowerCase();
		return userRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public User registerUser(String firstName, String lastName, String email, String password) throws TfAuthException {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if (email != null) email = email.toLowerCase();
		if (!pattern.matcher(email).matches()) throw new TfAuthException("Invalid email format");
		Boolean exists = userRepo.doesEmailExist(email);
		if (exists) throw new TfAuthException("Email already in use");
		Integer userId = userRepo.create(firstName, lastName, email, password);
		return userRepo.findById(userId);
	}
}
