package com.ms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ms.domain.ChatMessage;
import com.ms.domain.ChatRoom;
import com.ms.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class ChatMessageRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@Autowired
	ChatMessageRepository chatMessageRepository;

	private User user;
	private User user2;
	private ChatRoom chatRoom;

	@After
	public void claenUp() {
		chatMessageRepository.deleteAll();
		chatRoomRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void findByChatRoomIdx_chatRoomIdx_returnMessageList() {
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

//		chatRoom = ChatRoom.builder()
//				.name("itemTitle")
//				.build();
		chatRoom = new ChatRoom();

//		ChatRoom chatRoom2 = ChatRoom.builder()
//				.name("itemTitle2")
//				.build();
		ChatRoom chatRoom2 = new ChatRoom();

		userRepository.save(user);
		//userRepository.save(user2);

		chatRoomRepository.save(chatRoom);
		chatRoomRepository.save(chatRoom2);

		chatMessageRepository.save(ChatMessage.builder()
				.user(user)
				.chatRoom(chatRoom)
				.message("테스트")
				.sendTime(Timestamp.valueOf(LocalDateTime.now()))
				.build());
		chatMessageRepository.save(ChatMessage.builder()
				.user(user)
				.chatRoom(chatRoom2)
				.message("테스트2")
				.sendTime(Timestamp.valueOf(LocalDateTime.now()))
				.build());

		//when
		List<ChatMessage> list = chatMessageRepository.findAllByChatRoom_ChatRoomIdx(1);

		//then
		assertThat(list.size()).isEqualTo(1);
		assertThat(list.get(0).getMessage()).isEqualTo("테스트");
	}

}
