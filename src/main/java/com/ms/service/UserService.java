package com.ms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.domain.User;
import com.ms.repository.UserRepository;

@Service
public class UserService {

	@Autowired UserRepository userRepository;

	public List getUser() {
		List<User> userList = userRepository.findAll();
		return userList;
	}
}
