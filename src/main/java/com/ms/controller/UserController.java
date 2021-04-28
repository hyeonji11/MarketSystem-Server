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

import com.ms.config.JwtTokenUtil;
import com.ms.domain.User;
import com.ms.dto.JwtResponse;
import com.ms.dto.UserDto;
import com.ms.dto.UserResponse;
import com.ms.service.JwtUserDetailsService;
import com.ms.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired UserService userService;
	@Autowired JwtUserDetailsService userDetailsService;
	@Autowired JwtTokenUtil jwtTokenUtil;

	@GetMapping("/info")
	public ResponseEntity<?> getInfo(@RequestParam String token) {
		String userId = jwtTokenUtil.getUsernameFromToken(token);
		User user = userService.getUserOne(userId);
		JwtResponse jwtResponse = new JwtResponse(token, user.getUserIdx(), user.getId(), user.getName());
		return new ResponseEntity(jwtResponse, HttpStatus.OK);
	}

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
		return new ResponseEntity("아이디 혹은 이메일이 옳지 않습니다.", resHeaders, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/update")
	public ResponseEntity<?> getUser(@RequestParam String id) {
		User user = userService.getUserOne(id);
		UserResponse res = userService.userToResponse(user);
		//UserDto result = userService.userToDto(user);
		//result.setPw("");
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserDto user) {
		User req = userService.getUserOne(user.getId());
		req.setName(user.getName());
		req.setPw(user.getPw());
		req.setEmail(user.getEmail());
		req.setPhone(user.getPhone());

		User result = userService.signUpUser(req);
		UserResponse res = userService.userToResponse(result);
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@GetMapping("/check")
	public ResponseEntity<?> checkId(@RequestParam String id) {
		User user = userService.getUserOne(id);

		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8");

		if(user != null)
			return new ResponseEntity("중복된 아이디입니다.", resHeaders, HttpStatus.OK);
		return new ResponseEntity("사용 가능합니다.", resHeaders, HttpStatus.OK);
	}
}
