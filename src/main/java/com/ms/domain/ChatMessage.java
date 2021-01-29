package com.ms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
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

	String messageType;
	String message;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	@ManyToOne
	@JoinColumn(name = "chatRoomIdx")
	ChatRoom chatRoom;

}
