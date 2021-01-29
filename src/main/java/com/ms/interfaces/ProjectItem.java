package com.ms.interfaces;

import com.ms.domain.Item;

public class ProjectItem {
	
	public ProjectItem(Item item) {
		this.itemIdx = item.getItemIdx();
		this.title = item.getTitle();
		this.content = item.getContent();
		this.userIdx = item.getUser().getUserIdx();
	}

	public int itemIdx;
	public int userIdx;
	public String title;
	public String content;
}
