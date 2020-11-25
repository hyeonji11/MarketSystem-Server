package com.ms.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	int itemIdx;

	int userIdx;
	String title;
	String content;
	Date returnDate;
	String charge;
	boolean type;

	@Builder
	public Item(int itemIdx, int userIdx, String title, String content, Date returnDate, String charge, boolean type) {
		this.itemIdx = itemIdx;
		this.userIdx = userIdx;
		this.title = title;
		this.content = content;
		this.returnDate = returnDate;
		this.charge = charge;
		this.type = type;
	}
	
	public void update(String title, String content, String charge, boolean type) {
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
	}
}
