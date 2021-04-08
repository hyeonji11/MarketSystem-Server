package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.interfaces.SearchItem;
import com.ms.service.MypageService;

@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired MypageService mypageService;

	@GetMapping("/")
	public ResponseEntity<?> mypageMain(@RequestParam String userId) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/sale")
	public ResponseEntity<?> saleList(@RequestParam String userId) {
		List<SearchItem> saleList = mypageService.getSaleList(userId);
		return new ResponseEntity(saleList, HttpStatus.OK);
	}

	@GetMapping("/purchase")
	public ResponseEntity<?> purchaseList(@RequestParam String userId) {
		List<SearchItem> purchaseList = mypageService.getPurchaseList(userId);
		return new ResponseEntity(purchaseList, HttpStatus.OK);
	}
}
