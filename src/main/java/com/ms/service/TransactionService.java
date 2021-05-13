package com.ms.service;

import org.springframework.stereotype.Service;

import com.ms.domain.ChatRoom;
import com.ms.domain.Transaction;
import com.ms.repository.ChatRoomRepository;
import com.ms.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionService {
	private final TransactionRepository transRepository;
	private final ChatRoomRepository crRepository;

	public void transactionSave(int chatRoomIdx) {
		ChatRoom chatRoom = crRepository.findByChatRoomIdx(chatRoomIdx);

		Transaction trans = Transaction.builder()
				.state("거래완료")
				.item(chatRoom.getItem())
				.user(chatRoom.getUser())
				.build();
		transRepository.save(trans);
	}
}
