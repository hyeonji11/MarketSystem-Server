package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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

	List<User> userList;

	@Before
	public void setUp() {
		userRepository.save(User.builder()
				.id("testId1")
				.pw("testPw1")
				.name("테스트1")
				.email("test1@daum.net")
				.phone("010-1101-1101")
				.build());

		userRepository.save(User.builder()
				.id("testId2")
				.pw("testPw2")
				.name("테스트2")
				.email("test2@naver.com")
				.phone("010-2222-2222")
				.build());

		userList = userRepository.findAll();

		itemRepository.save(Item.builder()
				.title("판매")
				.charge("2000")
				.user(userList.get(0))
				.content("판매합니다")
				.type(true)
				.registrationDate(LocalDateTime.now())
				.returnDate(LocalDate.now())
				.build());
	}

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
		Item item = itemRepository.findAll().get(0);

		transRepository.save(Transaction.builder()
				.state(state)
				.item(item)
				.user(userList.get(1))
				.build());

		//when
		Transaction trans = transRepository.findAll().get(0);

		//then
		assertThat(trans.getTransactionIdx()).isEqualTo(1);
		assertThat(trans.getState()).isEqualTo(state);
		assertThat(trans.getItem()).isEqualTo(item);
		assertThat(trans.getUser()).isEqualTo(userList.get(1));
	}

	@Test
	public void findItemIdxByTraderIdx_userIdx_itemIdxList() {
		//given
		String state="판매완료";
		Item item = itemRepository.findAll().get(0);

		transRepository.save(Transaction.builder()
				.state(state)
				.item(item)
				.user(userList.get(1))
				.build());

		//when
		List<Integer> itemIdxList = transRepository.findItemIdxByUserIdx(userList.get(1).getUserIdx());

		//then
		assertThat(itemIdxList.get(0)).isEqualTo(item.getItemIdx());
	}

}
