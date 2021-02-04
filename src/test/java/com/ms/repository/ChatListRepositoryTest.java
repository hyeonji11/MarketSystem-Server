package com.ms.repository;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class ChatListRepositoryTest {

	@Autowired
	ChatListRepository chatListRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@After
	public void claenUp() {
		chatListRepository.deleteAll();
	}

}
