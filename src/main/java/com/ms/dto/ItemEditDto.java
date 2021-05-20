package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ms.domain.Item;

public class ItemEditDto {
	public int itemIdx;
	public String title;
	public String content;
	public String charge;
	public boolean type;
	public LocalDateTime registrationDate;
	public LocalDate returnDate;
	public List<byte[]> imageList;
	public List<String> imageName;

	public void setImageList(List<byte[]> imageList) {
		this.imageList = imageList;
	}

	public void setImageName(List<String> imageName) {
		this.imageName = imageName;
	}

	public ItemEditDto(Item item) {
		this.itemIdx = item.getItemIdx();
		this.title = item.getTitle();
		this.content = item.getContent();
		this.charge = item.getCharge();
		this.type = item.isType();
		this.registrationDate = item.getRegistrationDate();
		this.returnDate = item.getReturnDate();
	}
}
