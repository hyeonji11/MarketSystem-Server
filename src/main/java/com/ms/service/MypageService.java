package com.ms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired ItemService itemService;

	public List<SearchItem> getMainSaleList(String userId) {
		User user = userRepository.findById(userId).get();
		List<Item> itemList = itemRepository.findFirst3ByUser_UserIdxOrderByItemIdxDesc(user.getUserIdx());
		return itemToSearchItem(itemList);
	}

	public List<SearchItem> getMainPurchaseList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Integer> itemIdxList = transRepository.findItemIdxByUserIdx(user.getUserIdx());
		List<Item> itemList;

		if(itemIdxList.size() > 3) {
			List<Integer> newList = new ArrayList<Integer>(itemIdxList.subList(0, 3));
			itemList = itemRepository.findByItemIdxIn(itemIdxList);
		} else {
			itemList = itemRepository.findByItemIdxIn(itemIdxList);
		}

		return itemToSearchItem(itemList);
	}

	public List<SearchItem> getSaleList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Item> itemList = itemRepository.findAllByUser_UserIdx(user.getUserIdx());

		return itemToSearchItem(itemList);
	}

	public List<SearchItem> getPurchaseList(String userId) {
		User user = userRepository.findById(userId).get();

		List<Integer> itemIdxList = transRepository.findItemIdxByUserIdx(user.getUserIdx());
		List<Item> itemList = itemRepository.findByItemIdxIn(itemIdxList);

		return itemToSearchItem(itemList);
	}

	public List<SearchItem> itemToSearchItem(List<Item> itemList) {
		List<SearchItem> siList = new ArrayList<>();

		for(Item item : itemList) {
			SearchItem si = new SearchItem(item);
			try {
				si.setImage(itemService.getImage(imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0).getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//si.setImage(imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0));
			siList.add(si);
		}
		// imageRepository 여러번 호출하는거 개선할 방법 찾기
		// 쿼리에 in 써서 하는 방법 상각해보기

		return siList;
	}
}
