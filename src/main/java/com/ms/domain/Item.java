package com.ms.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int itemIdx;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	String title;
	String content;
	String charge;
	boolean type;

	LocalDateTime registrationDate;
	LocalDate returnDate;

	@Builder
	public Item(User user, String title, String content, String charge, boolean type, LocalDateTime registrationDate, LocalDate returnDate) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
		this.returnDate = returnDate;
	} 
	
	@Builder
	public void update(String title, String content, String charge,
			boolean type, LocalDateTime registrationDate, LocalDate returnDate) {
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
		this.returnDate = returnDate;
	}
	
}
