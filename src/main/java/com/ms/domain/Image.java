package com.ms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int imageIdx;
	
	@ManyToOne
	@JoinColumn(name = "itemIdx")
	Item item;
	
	String imageOriName;
	String imageUrl;
	
	/* @Builder
	public Image(int imgaeIdx, Item item, String imageName, String imageOriName, String imageUrl) {
		this.imageIdx = imageIdx;
		this.item = item;
		this.imageName = imageName;
		this.imageOriName = imageOriName;
		this.imageUrl = imageUrl;
	} */
}