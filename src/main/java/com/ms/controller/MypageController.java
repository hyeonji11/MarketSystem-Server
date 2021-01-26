package com.ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
public class MypageController {

	@GetMapping("/")
	public ResponseEntity<?> mypageMain() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/evaluation")
	public ResponseEntity<?> evaluationList() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/sale")
	public ResponseEntity<?> saleList() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/purchase")
	public ResponseEntity<?> purchaseList() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
