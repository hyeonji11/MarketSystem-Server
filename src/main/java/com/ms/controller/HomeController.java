package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.User;
import com.ms.service.UserService;

@RestController
public class HomeController {

	@Autowired UserService userService;

	@RequestMapping("/")
	public String main(String a) {
		return a;
	}

	@RequestMapping("/test")
	public ResponseEntity<?> test() {
		List<User> userList = userService.getUser();

		return new ResponseEntity(userList, HttpStatus.OK);
	}
}
