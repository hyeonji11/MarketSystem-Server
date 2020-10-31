package com.ms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	int itemIdx;

	int userIdx;
	String title;
	String content;
	Date returnDate;
	String charge;
	boolean type;
	
}
