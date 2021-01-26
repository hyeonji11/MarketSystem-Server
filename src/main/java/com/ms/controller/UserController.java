package com.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.User;
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
	public ResponseEntity<String> findPw(@RequestBody UserDto user) {
		boolean isFind = userService.findPw(user.getId(), user.getEmail());

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		if(isFind) {
			return new ResponseEntity("메일 전송 완료", resHeaders, HttpStatus.OK);
		}
		return new ResponseEntity("아이디 혹은 이메일이 옳지 않습니다.", resHeaders, HttpStatus.OK);
	}

	@GetMapping("/update")
	public ResponseEntity<?> getUser(@RequestParam String id) {
		User user = userService.getUserOne(id);
		UserDto result = userService.userToDto(user);
		result.setPw("");
		return new ResponseEntity(result, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserDto user) {
		User req = userService.getUserOne(user.getId());
		req.setName(user.getName());
		req.setPw(user.getPw());
		req.setEmail(user.getEmail());
		req.setPhone(user.getPhone());

		User result = userService.signUpUser(req);
		//String userId = userDetailsService.save(user);
		UserDto dto = userService.userToDto(result);
		return new ResponseEntity(dto, HttpStatus.OK);
	}
}
