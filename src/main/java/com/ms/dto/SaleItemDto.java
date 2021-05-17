package com.ms.dto;

import com.ms.domain.Item;

public class SaleItemDto {
	public int itemIdx;
	public String title;
	public String charge;
	public String state;
	//public Image images;
	public byte[] image;

//	public void setImage(Image images) {
//		this.images = images;
//	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SaleItemDto(Item item) {
		this.itemIdx = item.getItemIdx();
		this.title = item.getTitle();
		this.charge = item.getCharge();
	}
}
