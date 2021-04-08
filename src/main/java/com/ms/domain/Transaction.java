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
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int transactionIdx;

	//대여완료(대여중), 반납완료, 판매완료
	String state;

	@ManyToOne
	@JoinColumn(name="itemIdx")
	Item item;

	@ManyToOne
	@JoinColumn(name="traderIdx")
	User user;

	@Builder
	public Transaction(String state, Item item, User user) {
		this.state = state;
		this.item = item;
		this.user = user;
	}
}
