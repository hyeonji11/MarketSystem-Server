package com.ms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.domain.Item;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.repository.ItemRepository;

@Service

public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	Item item;
	
	public Integer saveItem(ItemSaveRequestDto itemSaveRequestDto) {
		Item item = new Item(itemSaveRequestDto.getItemIdx(), itemSaveRequestDto.getUserIdx(), itemSaveRequestDto.getTitle(), itemSaveRequestDto.getContent(), itemSaveRequestDto.getReturnDate(), itemSaveRequestDto.getCharge(),itemSaveRequestDto.isType());
		itemRepository.save(item);
		
		return itemSaveRequestDto.getItemIdx();
	}
	
	public List<Item> findAll() {
		return itemRepository.findAll();		
	}
	
	public Optional<Item> findById(Integer itemIdx) {
		return itemRepository.findById(itemIdx);
	}
	
	public Integer update(int itemIdx, ItemUpdateRequestDto itemUpdateRequestDto) {
		Item item = itemRepository.findById(itemIdx).orElseThrow(() -> new IllegalArgumentException("해당 아이템 없음"));
		item.update(itemUpdateRequestDto.getTitle(), itemUpdateRequestDto.getContent(), itemUpdateRequestDto.getCharge(), itemUpdateRequestDto.isType());
		
		return itemIdx;
	}
	
	public Integer delete(int itemIdx) {
		itemRepository.deleteById(itemIdx);
		return itemIdx;
	}
}
