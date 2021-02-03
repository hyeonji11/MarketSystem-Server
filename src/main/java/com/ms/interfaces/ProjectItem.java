package com.ms.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ms.domain.Item;

public class ProjectItem {
	
	public int itemIdx;
	public int userIdx;
	public String title;
	public String content;
	public String charge;
	public boolean type;
	public LocalDateTime registrationDate;
	public LocalDate returnDate;

	public ProjectItem(Item item) {
		this.itemIdx = item.getItemIdx();
		this.userIdx = item.getUser().getUserIdx();
		this.title = item.getTitle();
		this.content = item.getContent();
		this.charge = item.getCharge();
		this.type = item.isType();
		this.registrationDate = item.getRegistrationDate();
		this.returnDate = item.getReturnDate();
	}
}
