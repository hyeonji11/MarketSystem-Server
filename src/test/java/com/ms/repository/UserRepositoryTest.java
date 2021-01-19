package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@After
	public void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	public void 회원가입_불러오기() {

		String id = "testId1";
		String name = "테스트1";
		String email = "testUser@naver.com";
		String phone = "000-0101-0101";

		userRepository.save(User.builder()
				.id(id)
				.pw("testPw1")
				.name(name)
				.email(email)
				.phone(phone)
				.build());

		//when
		List<User> userList = userRepository.findAll();
		Optional<User> user1 = userRepository.findById(id);
		User user2 = userRepository.findByEmailAndName(email, name);
		User user3 = userRepository.findByIdAndEmail(id, email);

		//then
		User user = userList.get(0);
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getName()).isEqualTo(name);

		user = user1.get();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getEmail()).isEqualTo(email);

		assertThat(user2.getId()).isEqualTo(id);

		assertThat(user3.getName()).isEqualTo(name);
		assertThat(user3.getPhone()).isEqualTo(phone);
	}

}
