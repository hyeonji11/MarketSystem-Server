package com.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.domain.ChatList;
import com.ms.domain.ChatRoom;
import com.ms.domain.User;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ChatRoomControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ChatRoomController chatRoomController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private ChatListRepository chatListRepository;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	private User user;
	private User user2;
	private ChatRoom chatRoom;
	//private ChatMessage chatMessage;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(chatRoomController).build();

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

//		ChatRoom chatRoom2 = ChatRoom.builder()
//				.name("itemTitle2")
//				.build();

		userRepository.save(user);
		userRepository.save(user2);

		chatRoomRepository.save(chatRoom);
		//chatRoomRepository.save(chatRoom2);

		chatListRepository.save(ChatList.builder()
				.user(user)
				.chatRoom(chatRoom)
				.build());

		chatListRepository.save(ChatList.builder()
				.user(user2)
				.chatRoom(chatRoom)
				.build());

//		chatListRepository.save(ChatList.builder()
//				.user(user)
//				.chatRoom(chatRoom2)
//				.build());
//		chatMessage = ChatMessage.builder()
//				.message("test")
//				.sendTime(Timestamp.valueOf(LocalDateTime.now()))
//				.user(user)
//				.chatRoom(chatRoom)
//				.build();

	}

	@After
	public void tearDown() {
		chatListRepository.deleteAll();
		chatRoomRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void roomList_userIdx_returnChatRooms() throws Exception {
		//given

		//when&then
//		MvcResult result = mockMvc.perform(get("/chat/list")
//				.param("userIdx", Integer.toString(user2.getUserIdx())))
//				.andExpect(status().isOk())
//				.andReturn();

		mockMvc.perform(get("/chat/list")
				.param("userIdx", Integer.toString(user2.getUserIdx())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$[0].name").value(user.getName()));

//		ObjectMapper mapper = new ObjectMapper();
//		String str = result.getResponse().getContentAsString();
//
//		List<ChatRoomResponseDto> list = mapper.readValue(str, List.class);
//
//		System.out.println(list.get(0).getName());
//		assertThat(list.size()).isEqualTo(1);
//		assertThat(list.get(0).getName()).isEqualTo(user.getName());
	}

	@Test
	@Ignore
	public void createRoom_joinUsers_returnChatRoomIdx() throws Exception {
		//given

		//when&then
		MvcResult result = mockMvc.perform(post("/chat/create")
				.param("itemIdx", Integer.toString(1))
				.param("userIdx", Integer.toString(user.getUserIdx())))
				.andExpect(status().isOk())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		String str = result.getResponse().getContentAsString();
		int roomIdx = mapper.readValue(str, Integer.class);

		List<ChatList> list = chatListRepository.findAll();
		assertThat(list.get(3).getChatRoom().getChatRoomIdx()).isEqualTo(roomIdx);
	}

	@Test
	@Ignore
	public void roomDetail_roomId_returnRoomInfo() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/chat/room/{chatRoomIdx}", chatRoom.getChatRoomIdx()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].userIdx").value(user.getUserIdx()))
				.andExpect(jsonPath("$[0].chatRoomIdx").value(chatRoom.getChatRoomIdx()));
	}
}
