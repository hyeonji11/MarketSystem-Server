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
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int chatRoomIdx;

	@ManyToOne
	@JoinColumn(name = "itemIdx")
	Item item;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	@Builder
    public ChatRoom(Item item, User user) {
        this.item = item;
        this.user = user;
    }

}
