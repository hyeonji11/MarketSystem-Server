package com.ms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String main(String a) {
		return a;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

}
