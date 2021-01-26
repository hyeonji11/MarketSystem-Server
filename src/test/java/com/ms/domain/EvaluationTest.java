package com.ms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EvaluationTest {

	@Test
	public void Evaluation_lombok_test() {
		int rating = 4;
		String review="좋음";

		User user = User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build();

		Evaluation evaluation = Evaluation.builder()
				.rating(rating)
				.review(review)
				.user(user)
				.build();

		assertThat(evaluation.getRating()).isEqualTo(rating);
		assertThat(evaluation.getReview()).isEqualTo(review);
		assertThat(evaluation.getUser().getId()).isEqualTo("testId1");

	}

}
