package com.ms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TransactionTest {

	@Test
	public void Transaction_lombok_test() {
		String state="판매완료";

		User user = User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build();

		Item item = new Item();

		Transaction tran = Transaction.builder()
				.state(state)
				.item(item)
				.user(user)
				.build();

		assertThat(tran.getState()).isEqualTo(state);
		assertThat(tran.getUser().getId()).isEqualTo(user.getId());
	}

}
