package com.ms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.domain.Item;
import com.ms.domain.Transaction;
import com.ms.domain.User;
import com.ms.dto.SaleItemDto;
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

	public List<SearchItem> getMainSaleList(int userIdx) {
		User user = userRepository.findById(userIdx).get();
		List<Item> itemList = itemRepository.findFirst3ByUser_UserIdxOrderByItemIdxDesc(user.getUserIdx());
		return itemToSearchItem(itemList);
	}

	public List<SearchItem> getMainPurchaseList(int userIdx) {
		User user = userRepository.findById(userIdx).get();

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

	public List<SaleItemDto> getSaleList(int userIdx) {
		List<Item> itemList = itemRepository.findAllByUser_UserIdx(userIdx);
		List<Integer> itemIdxList = new ArrayList<Integer>();
		for(Item item : itemList) {
			itemIdxList.add(item.getItemIdx());
		}
		List<Transaction> transList = transRepository.findByItem_ItemIdxIn(itemIdxList);
		//transactionRepository에서 itemIdxList로 stateList 가져오는 코드

		return itemToSaleItemList(itemList, transList);
	}

	public List<SaleItemDto> itemToSaleItemList(List<Item> itemList, List<Transaction> transList) {
		List<SaleItemDto> sdList = new ArrayList<>();

		for(int i=0; i<itemList.size(); i++) {
			SaleItemDto sd = new SaleItemDto(itemList.get(i));
			try {
				sd.setImage(itemService.getImage(imageRepository.findAllByItem_ItemIdx(itemList.get(i).getItemIdx()).get(0).getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int j=0; j<transList.size(); j++) {
				if(itemList.get(i).getItemIdx() == transList.get(j).getItem().getItemIdx()) {
					sd.setState(transList.get(j).getState());
					transList.remove(j);
					break;
				}
			}
			sdList.add(sd);
		}

		return sdList;
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
			siList.add(si);
		}
		// imageRepository 여러번 호출하는거 개선할 방법 찾기
		// 쿼리에 in 써서 하는 방법 상각해보기

		return siList;
	}
}
