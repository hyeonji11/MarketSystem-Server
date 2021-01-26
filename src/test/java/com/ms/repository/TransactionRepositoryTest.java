package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.Item;
import com.ms.domain.Transaction;
import com.ms.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class TransactionRepositoryTest {

	@Autowired
	TransactionRepository transRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	@After
	public void cleanup() {
		transRepository.deleteAll();
		itemRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void repository_test() {
		//given
		String state="판매완료";

		userRepository.save(User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build());
		User testUser = userRepository.findAll().get(0);

		itemRepository.save(Item.builder()
				.itemIdx(1)
				.title("판매")
				.charge("2000")
				.userIdx(testUser.getUserIdx())
				.content("판매합니다")
				.type(true)
				.returnDate(new Date())
				.build());
		Item item = itemRepository.findAll().get(0);

		transRepository.save(Transaction.builder()
				.state(state)
				.item(item)
				.user(testUser)
				.build());

		//when
		Transaction trans = transRepository.findAll().get(0);

		//then
		assertThat(trans.getTransactionIdx()).isEqualTo(1);
		assertThat(trans.getState()).isEqualTo(state);
		assertThat(trans.getItem()).isEqualTo(item);
		assertThat(trans.getUser()).isEqualTo(testUser);
	}

}
