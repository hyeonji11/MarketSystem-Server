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

import com.ms.dto.ReportListResponseDto;
import com.ms.dto.ReportRequestDto;
import com.ms.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired ReportService reportService;

	@PostMapping
	public ResponseEntity<?> insertReport(@RequestBody ReportRequestDto reportDto) {
		reportService.saveReport(reportDto);
		return new ResponseEntity("sign up success", HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<ReportListResponseDto> reportList(@RequestParam String userId) {
		ReportListResponseDto report = new ReportListResponseDto();
		report.setReportList(reportService.getReportList(userId));
		return new ResponseEntity(report, HttpStatus.OK);
	}
}
