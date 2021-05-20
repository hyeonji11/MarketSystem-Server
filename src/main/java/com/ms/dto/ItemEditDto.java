package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ms.domain.Item;

public class ItemEditDto {
	public int itemIdx;
	public String title;
	public String content;
	public String charge;
	public boolean type;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public LocalDateTime registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
