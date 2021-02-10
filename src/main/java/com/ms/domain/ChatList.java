package com.ms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int chatListIdx;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	@ManyToOne
	@JoinColumn(name = "chatRoomIdx")
	ChatRoom chatRoom;

	@Builder
	public ChatList(User user, ChatRoom chatRoom) {
		this.user = user;
		this.chatRoom = chatRoom;
	}

}