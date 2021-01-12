package com.ms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UserTest {

	@Test
	public void 롬복_기능_테스트() {
		String id = "테스트1";
		String pw = "test1";
		String name = "user1";
		String email = "user@naver.com";
		String phone = "000-0000-0000";

		User user = User.builder()
				.id(id)
				.pw(pw)
				.name(name)
				.email(email)
				.phone(phone)
				.build();

		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getName()).isEqualTo(name);
	}

}
