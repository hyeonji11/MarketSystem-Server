package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.Evaluation;
import com.ms.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class EvaluationRepoisitoryTest {

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	UserRepository userRepository;

	@After
	public void cleanup() {
		evaluationRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void repository_test() {
		//given
		int rating = 3;
		String review = "나쁘지 않음";

		userRepository.save(User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build());

		User testUser = userRepository.findAll().get(0);

		evaluationRepository.save(Evaluation.builder()
				.rating(rating)
				.review(review)
				.user(testUser)
				.build());

		//when
		Evaluation eval = evaluationRepository.findAll().get(0);

		//then
		assertThat(eval.getEvaluationIdx()).isEqualTo(1);
		assertThat(eval.getRating()).isEqualTo(rating);
		assertThat(eval.getReview()).isEqualTo(review);
		assertThat(eval.getUser()).isEqualTo(testUser);
	}
}
