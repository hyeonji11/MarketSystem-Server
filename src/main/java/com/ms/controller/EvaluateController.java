package com.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.EvalListResponseDto;
import com.ms.dto.EvalRequestDto;
import com.ms.service.EvaluateService;

@RestController
@RequestMapping("/eval")
public class EvaluateController {

	@Autowired EvaluateService evalService;

	@PostMapping
	public ResponseEntity<?> insertEvaluation(@RequestBody EvalRequestDto dto) {
		evalService.saveEvaluation(dto);
		return new ResponseEntity("평가 완료", HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<EvalListResponseDto> evaluationList(@RequestParam Integer userIdx) {
		EvalListResponseDto res = new EvalListResponseDto();
		res.setEvalList(evalService.getEvaluationList(userIdx));
		res.setAverage(evalService.getEvalAverage(userIdx));
		return new ResponseEntity(res, HttpStatus.OK);
	}
}
