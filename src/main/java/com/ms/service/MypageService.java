package com.ms.service;

import org.springframework.stereotype.Service;

import com.ms.repository.EvaluationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {
	private final EvaluationRepository evalRepository;
}
