package com.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.UserDto;
import com.ms.service.JwtUserDetailsService;
import com.ms.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired UserService userService;
	@Autowired JwtUserDetailsService userDetailsService;

//	@PostMapping("/signup")
//	public ResponseEntity<?> signUp(@RequestBody User user) {
//		userService.signUpUser(user);
//
//		return new ResponseEntity("success", HttpStatus.OK);
//	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDto user) {
		userDetailsService.save(user);

		return new ResponseEntity("sign up success", HttpStatus.OK);
	}

	@RequestMapping("/logout/result")
	public ResponseEntity<?> logout() {
		return new ResponseEntity("logout success", HttpStatus.OK);
	}
}
