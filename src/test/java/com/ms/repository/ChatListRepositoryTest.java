package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.ChatList;
import com.ms.domain.ChatRoom;
import com.ms.domain.User;
import com.ms.dto.ChatRoomResponseDto;

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

	private User user;
	private User user2;
	private ChatRoom chatRoom;

	@After
	public void claenUp() {
		chatListRepository.deleteAll();
	}

	@Test
	public void findUserByChatRoom_userIdx_returnList() {
		//given
		user = User.builder()
				.id("id3")
				.pw("pw3")
				.email("test3333@naver.com")
				.name("사용자333")
				.phone("010-3333-3333")
				.build();

		user2 = User.builder()
				.id("id2")
				.pw("pw2")
				.email("test22@naver.com")
				.name("사용자22")
				.phone("010-2222-2222")
				.build();

		chatRoom = ChatRoom.builder()
				.name("itemTitle")
				.build();

		userRepository.save(user);
		userRepository.save(user2);

		chatRoomRepository.save(chatRoom);

		chatListRepository.save(ChatList.builder()
				.user(user)
				.chatRoom(chatRoom)
				.build());

		chatListRepository.save(ChatList.builder()
				.user(user2)
				.chatRoom(chatRoom)
				.build());

		//when
		//Map<Integer, String> list = chatListRepository.findUserByChatRoom(user.getUserIdx());
		List<ChatRoomResponseDto> list = chatListRepository.findUserByChatRoom(user.getUserIdx());

		//then
		assertThat(list.get(0).getName()).isEqualTo(user2.getName());

	}

}
