package com.ms.interfaces;

import com.ms.domain.Item;

public class SearchItem {

	public int itemIdx;
	public String title;
	public String charge;
	//public Image images;
	public byte[] image;

//	public void setImage(Image images) {
//		this.images = images;
//	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public SearchItem(Item item) {
		this.itemIdx = item.getItemIdx();
		this.title = item.getTitle();
		this.charge = item.getCharge();
	}
}
