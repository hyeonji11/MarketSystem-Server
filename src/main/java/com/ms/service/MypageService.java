package com.ms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.interfaces.SearchItem;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.TransactionRepository;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {
	private final ItemRepository itemRepository;
	private final ImageRepository imageRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transRepository;

	public List<SearchItem> getSaleList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Item> itemList = itemRepository.findAllByUser_UserIdx(user.getUserIdx());
		List<SearchItem> result = new ArrayList<>(); //1

		for(Item item : itemList) { //2
			SearchItem si = new SearchItem(item);
			si.setImage(imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0));
			result.add(si);
		}

		return result;
	}

	public List<SearchItem> getPurchaseList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Integer> itemIdxList = transRepository.findItemIdxByUserIdx(user.getUserIdx());
		List<Item> itemList = itemRepository.findByItemIdxIn(itemIdxList);

		List<SearchItem> result = new ArrayList<>(); //1

		for(Item item : itemList) { //2
			SearchItem si = new SearchItem(item);
			si.setImage(imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0));
			result.add(si);
		}

		// 1, 2번 코드 중복 없애기
		// imageRepository 여러번 호출하는거 개선할 방법 찾기

		return result;
	}
}
