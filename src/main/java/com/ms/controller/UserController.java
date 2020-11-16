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

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDto user) {
		userDetailsService.save(user);

		return new ResponseEntity("sign up success", HttpStatus.OK);
	}

	@RequestMapping("/logout/result")
	public ResponseEntity<?> logout() {
		return new ResponseEntity("logout success", HttpStatus.OK);
	}

	@PostMapping("/findid")
	public ResponseEntity<?> findId(@RequestBody UserDto user) {
		String userId = userService.findId(user.getEmail(), user.getName());
		return new ResponseEntity(userId, HttpStatus.OK);
	}

	@PostMapping("/findpw")
	public ResponseEntity<?> findPw(@RequestBody UserDto user) {
		boolean isFind = userService.findPw(user.getId(), user.getEmail());
		if(isFind) {
			return new ResponseEntity("메일 전송 완료", HttpStatus.OK);
		}
		return new ResponseEntity("아이디를 찾지 못했습니다", HttpStatus.OK);
	}
}
