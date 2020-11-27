package com.mata.taskforce.services;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mata.taskforce.exceptions.TfAuthException;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.model.User;
import com.mata.taskforce.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImp implements UserService {
	@Autowired
	UserRepository userRepo;

	@Override
	public User validateUser(String email, String password) {
		if (email == null || password == null) throw new TfBadRequestException("All fields must be provided"); 
		email = email.toLowerCase();
		User user = userRepo.findByEmail(email);
		if (user == null) throw new TfAuthException("Invalid email or password");
		if (!BCrypt.checkpw(password, user.getPassword())) throw new TfAuthException("Invalid email or password");
		return user;
	}

	@Override
	public User registerUser(User user) {
		if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) 
			throw new TfBadRequestException("All fields must be provided");
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
		user.setEmail(user.getEmail().toLowerCase());
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if (!pattern.matcher(user.getEmail()).matches()) throw new TfBadRequestException("Invalid email format");		
		if (userRepo.existsByEmail(user.getEmail())) throw new TfBadRequestException("Email already in use");
		return userRepo.save(user);
	}
}