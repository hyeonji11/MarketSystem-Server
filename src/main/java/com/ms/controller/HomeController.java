package com.ms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	//@Autowired UserService userService;

	@RequestMapping("/")
	public String main(String a) {
		return a;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

//	@RequestMapping("/test")
//	public ResponseEntity<?> test() {
//		List<User> userList = userService.getUser();
//
//		return new ResponseEntity(userList, HttpStatus.OK);
//	}
}
