package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.Evaluation;
import com.ms.domain.User;
import com.ms.dto.EvalResponseDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class EvaluationRepoisitoryTest {

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	UserRepository userRepository;

	int rating = 3;
	String review = "나쁘지 않음";

	User testUser;

	@Before
	public void setUp() {
		userRepository.save(User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build());

		testUser = userRepository.findAll().get(0);

		evaluationRepository.save(Evaluation.builder()
				.rating(rating)
				.review(review)
				.user(testUser)
				.build());
	}

	@After
	public void cleanup() {
		evaluationRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void repository_test() {
		//given


		//when
		Evaluation eval = evaluationRepository.findAll().get(0);

		//then
		assertThat(eval.getRating()).isEqualTo(rating);
		assertThat(eval.getReview()).isEqualTo(review);
		assertThat(eval.getUser()).isEqualTo(testUser);
	}

	@Test
	public void findAllByuserIdx_userIdx_evalResponseDtoList() {
		//given
		int userIdx = testUser.getUserIdx();

		//when
		List<EvalResponseDto> list = evaluationRepository.findAllByUserIdx(userIdx);

		//then
		assertThat(list.size()).isEqualTo(1);
		assertThat(list.get(0).getRating()).isEqualTo(rating);
		assertThat(list.get(0).getReview()).isEqualTo(review);
	}

	@Test
	public void getAverate_userIdx_average() {
		//given
		evaluationRepository.save(Evaluation.builder()
				.rating(4)
				.review("친절해요")
				.user(testUser)
				.build());

		//when
		double avg = evaluationRepository.getAverage(testUser.getUserIdx());

		//then
		assertThat(avg).isEqualTo(3.5);
	}
}
