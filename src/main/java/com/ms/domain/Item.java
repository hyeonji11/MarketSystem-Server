package com.ms.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

	//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate returnDate;

	String charge;
	boolean type;

	@Builder
	public Item(User user, String title, String content, LocalDate returnDate, String charge, boolean type) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.returnDate = returnDate;
		this.charge = charge;
		this.type = type;
	}

	@Builder
	public void update(String title, String content, String charge, boolean type) {

			this.title = title;
			this.content = content;

			this.charge = charge;

			this.type = type;
	}
}
