package com.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.domain.ChatList;
import com.ms.domain.ChatMessage;
import com.ms.domain.ChatRoom;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ChatCreateRequestDto;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;
import com.ms.repository.ItemRepository;
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
	private ItemRepository itemRepository;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private ChatListRepository chatListRepository;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	private User user;
	private User user2;
	private ChatRoom chatRoom;

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

	}

	@After
	public void tearDown() {
		chatListRepository.deleteAll();
		chatMessageRepository.deleteAll();
		chatRoomRepository.deleteAll();
		itemRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void roomList_userIdx_returnChatRooms() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/chat/list")
				.param("userIdx", Integer.toString(user.getUserIdx())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$[0].name").value(user2.getName()));
	}

	@Test
	public void createRoom_joinUsers_returnChatRoomIdx() throws Exception {
		//given
		itemRepository.save(Item.builder()
				.user(user2)
				.title("item1")
				.content("content1")
				.charge("1000")
				.build());

		ChatCreateRequestDto createDto = new ChatCreateRequestDto();
		createDto.setItemIdx(1);
		createDto.setUserIdx(1);

		//when&then
		mockMvc.perform(post("/chat/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(createDto)))
				.andExpect(status().isOk());

		List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
		assertThat(chatRoomList.size()).isEqualTo(2);

		List<ChatList> chatList = chatListRepository.findAll();
		assertThat(chatList.size()).isEqualTo(4);
	}

	@Test
	public void roomDetail_chatRoomIdx_returnChatMessages() throws Exception {
		//given
		Timestamp time = Timestamp.valueOf(LocalDateTime.now());
		ChatMessage chatMessage = ChatMessage.builder()
				.message("test")
				.sendTime(time)
				.user(user)
				.chatRoom(chatRoom)
				.build();

		chatMessageRepository.save(chatMessage);

		LocalDateTime ldt = chatMessage.getSendTime().toLocalDateTime();
		String str = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		//when&then
		mockMvc.perform(get("/chat/room/{chatRoomIdx}", chatRoom.getChatRoomIdx()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].userIdx").value(user.getUserIdx()))
				.andExpect(jsonPath("$[0].chatRoomIdx").value(chatRoom.getChatRoomIdx()))
				.andExpect(jsonPath("$[0].message").value(chatMessage.getMessage()))
				.andExpect(jsonPath("$[0].sendTime").value(str));
	}
}
