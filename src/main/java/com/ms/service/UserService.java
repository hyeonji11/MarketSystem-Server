package com.ms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.domain.User;
import com.ms.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List getUser() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Transactional
	public void signUpUser(User user) {
		user.setPw(passwordEncoder.encode(user.getPw()));
		userRepository.save(user);
	}
}
