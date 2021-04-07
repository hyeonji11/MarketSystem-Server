package com.ms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.interfaces.SearchItem;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {
	private final ItemRepository itemRepository;
	private final ImageRepository imageRepository;
	private final UserRepository userRepository;

	public List<SearchItem> getSaleList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Item> itemList = itemRepository.findAllByUser_UserIdx(user.getUserIdx());
		List<SearchItem> result = new ArrayList<>();

		for(Item item : itemList) {
			SearchItem si = new SearchItem(item);
			si.setImage(imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0));
			result.add(si);
		}

		return result;
	}

	public List<SearchItem> getPurchaseList(String userId) {
		return new ArrayList<>();
	}
}
