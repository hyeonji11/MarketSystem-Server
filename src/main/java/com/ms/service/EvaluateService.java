package com.ms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.domain.Evaluation;
import com.ms.domain.User;
import com.ms.dto.EvalRequestDto;
import com.ms.dto.EvalResponseDto;
import com.ms.repository.EvaluationRepository;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EvaluateService {

	private final EvaluationRepository evaluationRepository;
	private final UserRepository userRepository;

	public void saveEvaluation(EvalRequestDto dto) {
		User user = userRepository.findById(dto.getUserId()).get();

		Evaluation eval = Evaluation.builder()
				.user(user)
				.rating(dto.getRating())
				.review(dto.getReview())
				.build();

		evaluationRepository.save(eval);
	}

	public List<EvalResponseDto> getEvaluationList(String userId) {
		User user = userRepository.findById(userId).get();
		List<EvalResponseDto> list = evaluationRepository.findAllByUserIdx(user.getUserIdx());
		return list;
	}

	public double getEvalAverage(String userId) {
		User user = userRepository.findById(userId).get();
		double avg = evaluationRepository.getAverage(user.getUserIdx());
		return avg;
	}

	public List<EvalResponseDto> getMypageEvalList(String userId) {
		User user = userRepository.findById(userId).get();
		List<EvalResponseDto> list = evaluationRepository.findAllByUserIdx(user.getUserIdx());
		if(list.size() > 2) {
			List<EvalResponseDto> newList = new ArrayList<>(list.subList(0, 2));
			return newList;
		}
		return list;
	}

}
