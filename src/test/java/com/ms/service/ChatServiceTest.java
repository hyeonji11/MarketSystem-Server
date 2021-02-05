package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ms.dto.ChatRoomResponseDto;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;

public class ChatServiceTest {

	@Mock
	private ChatRoomRepository chatRoomRepository;

	@Mock
	private ChatListRepository chatListRepository;

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
		ChatRoomResponseDto dto2 = new ChatRoomResponseDto(2, 2, "사용자2");

		List<ChatRoomResponseDto> list = new ArrayList<>();
		list.add(dto);
		list.add(dto2);

		Mockito.when(chatListRepository.findUserByChatRoom(userIdx))
				.thenReturn(list);

		//when
		List<ChatRoomResponseDto> chatRoomList = chatService.findAllRoom(userIdx);

		//then
		assertThat(chatRoomList.size()).isEqualTo(1);
	}

}
