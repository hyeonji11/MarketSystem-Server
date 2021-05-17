package com.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.MypageListDto;
import com.ms.dto.SaleItemDto;
import com.ms.interfaces.SearchItem;
import com.ms.service.EvaluateService;
import com.ms.service.MypageService;
import com.ms.service.UserService;

@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired MypageService mypageService;
	@Autowired EvaluateService evalService;
	@Autowired UserService userService;

	@GetMapping("")
	public ResponseEntity<?> mypageMain(@RequestParam Integer userIdx) {
		MypageListDto dto = new MypageListDto();
		dto.setEvalList(evalService.getMypageEvalList(userIdx));
		dto.setPurchaseList(mypageService.getMainPurchaseList(userIdx));
		dto.setSaleList(mypageService.getMainSaleList(userIdx));
		dto.setUserDto(userService.getUserInfo(userIdx));
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	@GetMapping("/sale")
	public ResponseEntity<?> saleList(@RequestParam Integer userIdx) {
		List<SaleItemDto> saleList = mypageService.getSaleList(userIdx);
		return new ResponseEntity(saleList, HttpStatus.OK);
	}

	@GetMapping("/purchase")
	public ResponseEntity<?> purchaseList(@RequestParam String userId) {
		List<SearchItem> purchaseList = mypageService.getPurchaseList(userId);
		return new ResponseEntity(purchaseList, HttpStatus.OK);
	}
}
