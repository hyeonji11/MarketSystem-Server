package com.ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.domain.Report;
import com.ms.domain.User;
import com.ms.dto.ReportRequestDto;
import com.ms.dto.ReportResponseDto;
import com.ms.repository.ReportRepository;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;

	public void saveReport(ReportRequestDto reportDto) {
		User user = userRepository.findById(reportDto.getUserId()).get();
		
		Report report = Report.builder()
				.user(user)
				.title(reportDto.getTitle())
				.content(reportDto.getContent())
				.build();

		reportRepository.save(report);
	}

	public List<ReportResponseDto> getReportList(String userId) {
		User user = userRepository.findById(userId).get();
		List<ReportResponseDto> list = reportRepository.findAllByUserIdx(user.getUserIdx());
		return list;
	}

}
