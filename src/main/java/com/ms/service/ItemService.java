package com.ms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;


@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	UserRepository userRepository;

	public Item saveItem(ItemSaveRequestDto itemSaveRequestDto) {
		System.out.println(itemSaveRequestDto.getUserId());
		
		Optional<User> one = userRepository.findById(itemSaveRequestDto.getUserId());
		
	      User user = one.get();
		Item item = Item.builder()
				.title(itemSaveRequestDto.getTitle())
				.user(user)
				.content(itemSaveRequestDto.getContent())
				.returnDate(LocalDate.now().plusDays(7))
				.charge(itemSaveRequestDto.getCharge())
				.type(itemSaveRequestDto.isType())
				.build();
				
		return itemRepository.save(item);
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
		itemRepository.save(item);
		
		return itemIdx;
	}

	public Integer delete(int itemIdx) {
		itemRepository.deleteById(itemIdx);
		return itemIdx;
	}
}
