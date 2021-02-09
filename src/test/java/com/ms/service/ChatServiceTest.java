package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ms.domain.ChatList;
import com.ms.domain.ChatMessage;
import com.ms.domain.ChatRoom;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ChatCreateRequestDto;
import com.ms.dto.ChatMessageDto;
import com.ms.dto.ChatMessageResponseDto;
import com.ms.dto.ChatRoomResponseDto;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;

public class ChatServiceTest {

	@Mock
	private ChatRoomRepository chatRoomRepository;

	@Mock
	private ChatListRepository chatListRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ChatMessageRepository chatMessageRepository;

	@InjectMocks
	private ChatService chatService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findAllRoom_userIdx_returnUserChatRoom() {
		//given
		int userIdx = 1;

		ChatRoomResponseDto dto = new ChatRoomResponseDto(1, 1, "사용자1");

		List<ChatRoomResponseDto> list = new ArrayList<>();
		list.add(dto);

		Mockito.when(chatListRepository.findUserByChatRoom(userIdx))
				.thenReturn(list);

		//when
		List<ChatRoomResponseDto> chatRoomList = chatService.findAllRoom(userIdx);

		//then
		assertThat(chatRoomList.size()).isEqualTo(1);
	}

	@Test
	public void createChatRoom_createDto_saveChatRoom() {
		//given
		ChatCreateRequestDto createDto = new ChatCreateRequestDto();
		createDto.setItemIdx(1);
		createDto.setUserIdx(1);

		User user = User.builder()
				.id("id3")
				.pw("pw3")
				.email("test3333@naver.com")
				.name("사용자333")
				.phone("010-3333-3333")
				.build();

		User user2 = User.builder()
				.id("id2")
				.pw("pw2")
				.email("test22@naver.com")
				.name("사용자22")
				.phone("010-2222-2222")
				.build();

		user.setUserIdx(1);
		user2.setUserIdx(2);

		Item item = Item.builder().title("title").user(user).build();
		Optional<Item> testItem = Optional.of(item);
		Optional<User> testUser1 = Optional.of(user);
		Optional<User> testUser2 = Optional.of(user2);

		Mockito.when(itemRepository.findById(1))
				.thenReturn(testItem);

		Mockito.when(userRepository.findById(1))
				.thenReturn(testUser1);

		Mockito.when(userRepository.findById(2))
				.thenReturn(testUser2);

		Mockito.when(chatRoomRepository.save(any(ChatRoom.class)))
				.thenReturn(new ChatRoom());

		Mockito.when(chatListRepository.save(any(ChatList.class)))
		.thenReturn(new ChatList());

		Mockito.when(chatListRepository.save(any(ChatList.class)))
		.thenReturn(new ChatList());

		//when
		chatService.createChatRoom(createDto);

		//then
		verify(chatRoomRepository).save(any(ChatRoom.class));
		verify(chatListRepository, times(2)).save(any(ChatList.class));
	}

	@Test
	public void insertChatMessage_message_saveMessage() {
		//given
		ChatMessageDto cmd = new ChatMessageDto();
		cmd.setChatRoomIdx(1);
		cmd.setUserIdx(1);
		cmd.setMessage("테스트입니다.");

		User user = User.builder()
				.id("id3")
				.pw("pw3")
				.email("test3333@naver.com")
				.name("사용자333")
				.phone("010-3333-3333")
				.build();

		ChatRoom chatRoom = ChatRoom.builder()
				.name("itemTitle")
				.build();

		Optional<ChatRoom> testChatRoom = Optional.of(chatRoom);
		Optional<User> testUser = Optional.of(user);

		Mockito.when(chatRoomRepository.findById(1))
				.thenReturn(testChatRoom);

		Mockito.when(userRepository.findById(1))
				.thenReturn(testUser);

		Mockito.when(chatMessageRepository.save(any(ChatMessage.class)))
				.thenReturn(new ChatMessage());

		//when
		chatService.insertChatMessage(cmd);

		//then
		verify(chatRoomRepository).findById(1);
		verify(userRepository).findById(1);
		verify(chatMessageRepository).save(any(ChatMessage.class));
	}

	@Test
	public void getChatMessages_chatRoomIdx_messageList() {
		//given
		int chatRoomIdx = 1;
		User user = new User();
		user.setUserIdx(1);

		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatRoomIdx(chatRoomIdx);

		ChatMessage chatMessage1 = ChatMessage.builder()
				.chatRoom(chatRoom)
				.user(user)
				.message("테스트1")
				.sendTime(Timestamp.valueOf(LocalDateTime.now()))
				.build();

		List<ChatMessage> testList = new ArrayList<>();
		testList.add(chatMessage1);


		Mockito.when(chatMessageRepository.findAllByChatRoom_ChatRoomIdx(chatRoomIdx))
				.thenReturn(testList);

		//when
		List<ChatMessageResponseDto> list = chatService.getChatMessages(chatRoomIdx);

		//then
		assertThat(list.get(0).getUserIdx()).isEqualTo(1);
		assertThat(list.get(0).getChatRoomIdx()).isEqualTo(chatRoomIdx);
		assertThat(list.get(0).getMessage()).isEqualTo(chatMessage1.getMessage());
	}

}
