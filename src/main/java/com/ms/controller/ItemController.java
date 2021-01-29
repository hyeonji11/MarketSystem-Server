package com.ms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.domain.Item;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ItemController {
	@Autowired
	ItemService itemService;

	// read list
	@GetMapping("/item/list")
	public String findAll(Model model) {
		List<Item> itemList = itemService.findAll();

		model.addAttribute("list", itemList);

		return "item/list";
	}

	// read one
	@GetMapping("/itme/{itemIdx}")
	public String findById(Model model, @PathVariable Integer itemIdx) {
		Optional<Item> item = itemService.findById(itemIdx);
		model.addAttribute("item", item);

		return "item/one";
	}

	// create
	@PostMapping("/item")
	public Integer save(@RequestBody ItemSaveRequestDto itemSaveRequestDto) {
		return itemService.saveItem(itemSaveRequestDto);
	}

	// update
	@PutMapping("/item/{itemIdx}")
	public Integer update(@PathVariable int itemIdx, @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
		return itemService.update(itemIdx, itemUpdateRequestDto);
	}

	// delete
	@DeleteMapping("/item/{itemIdx}")
	public Integer delete(@PathVariable int itemIdx) {
		return itemService.delete(itemIdx);
	}
}
