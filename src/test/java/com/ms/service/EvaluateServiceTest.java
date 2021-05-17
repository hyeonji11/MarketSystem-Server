package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ms.domain.Evaluation;
import com.ms.domain.User;
import com.ms.dto.EvalRequestDto;
import com.ms.dto.EvalResponseDto;
import com.ms.repository.EvaluationRepository;
import com.ms.repository.UserRepository;

public class EvaluateServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private EvaluationRepository evalRepository;

	@InjectMocks
	private EvaluateService evaluateService;

	private User testUser;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		testUser = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("test1111@naver.com")
				.phone("010-1111-1111")
				.build();
		userRepository.save(testUser);
	}

	@After
	public void clear() {
		evalRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void saveEvaluation_evalRequestDto() {
		//given
		EvalRequestDto dto = new EvalRequestDto();
		dto.setUserId("id1");
		dto.setRating(3);
		dto.setReview("나쁘지않음");

		Optional<User> user = Optional.of(testUser);

		Evaluation eval = Evaluation.builder()
				.rating(3)
				.review("나쁘지않음")
				.user(testUser)
				.build();

		Mockito.when(userRepository.findById(dto.getUserId()))
				.thenReturn(user);

		Mockito.when(evalRepository.save(eval))
				.thenReturn(eval);

		//when
		evaluateService.saveEvaluation(dto);

		//then
		verify(userRepository).findById(dto.getUserId());
		verify(evalRepository).save(any(Evaluation.class));
	}

	@Test
	public void getEvaluationList_userId_EvalResponseDtoList() {
		//given
		String userId = "id1";
		int userIdx = 1;
		testUser.setUserIdx(userIdx);

		Optional<User> user = Optional.of(testUser);

		EvalResponseDto dto = new EvalResponseDto(3, "좋음");

		List<EvalResponseDto> list = new ArrayList<>();
		list.add(dto);

		Mockito.when(userRepository.findById(userId))
				.thenReturn(user);

		Mockito.when(evalRepository.findAllByUserIdx(userIdx))
				.thenReturn(list);

		//when
		List<EvalResponseDto> res = evaluateService.getEvaluationList(userIdx);

		//then
		verify(userRepository).findById(userId);
		verify(evalRepository).findAllByUserIdx(userIdx);
	}

	@Test
	public void getEvalAverage_userId_evaluationAvg() {
		//given
		String userId = "id1";

		int userIdx = 1;
		testUser.setUserIdx(userIdx);

		Optional<User> user = Optional.of(testUser);

		Mockito.when(userRepository.findById(userId))
		.thenReturn(user);

		Mockito.when(evalRepository.getAverage(userIdx))
		.thenReturn(3.5);

		//when
		double avg = evaluateService.getEvalAverage(userIdx);

		//then
		verify(userRepository).findById(userId);
		verify(evalRepository).getAverage(userIdx);
	}

	@Test
	public void getMypageEvalList_userId_EvalResponseDtoList() {
		//given
		String userId = "id1";
		int userIdx = 1;
		testUser.setUserIdx(userIdx);

		Optional<User> user = Optional.of(testUser);

		EvalResponseDto dto = new EvalResponseDto(3, "좋음");
		EvalResponseDto dto2 = new EvalResponseDto(5, "매우좋음");
		EvalResponseDto dto3 = new EvalResponseDto(2, "약속 늦음");
		EvalResponseDto dto4 = new EvalResponseDto(4, "친절함");

		List<EvalResponseDto> list = new ArrayList<>();
		list.add(dto);
		list.add(dto2);
		list.add(dto3);
		list.add(dto4);

		Mockito.when(userRepository.findById(userId))
				.thenReturn(user);

		Mockito.when(evalRepository.findAllByUserIdx(userIdx))
				.thenReturn(list);

		//when
		List<EvalResponseDto> res = evaluateService.getMypageEvalList(userIdx);

		//then
		verify(userRepository).findById(userId);
		verify(evalRepository).findAllByUserIdx(userIdx);
		assertThat(res.size()).isEqualTo(2);
	}
}
