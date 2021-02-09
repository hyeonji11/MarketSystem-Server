package com.ms.domain;

import java.sql.Timestamp;

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
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int chatMessageIdx;

	String message;
	Timestamp sendTime;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	@ManyToOne
	@JoinColumn(name = "chatRoomIdx")
	ChatRoom chatRoom;

	@Builder
	public ChatMessage(String message, Timestamp sendTime, User user, ChatRoom chatRoom) {
		this.message = message;
		this.sendTime = sendTime;
		this.user = user;
		this.chatRoom = chatRoom;
	}

}
