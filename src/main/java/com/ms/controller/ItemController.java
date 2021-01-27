package com.ms.controller;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.Item;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.service.ItemService;
import com.ms.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ItemController {
	
	@Autowired	ItemService itemService;
	@Autowired	UserService userService;

	// read list
	@GetMapping("/list")
	public List<Item> item() {		 
		return itemService.findAll();
	}

	// read one
	@GetMapping("/{itemIdx}")
	public Item item(@PathVariable("itemIdx") int itemIdx) {
		return itemService.findById(itemIdx).get();
	}

	// create
	@PostMapping("/save")
	public String save(@RequestBody ItemSaveRequestDto itemSaveRequestDto) {
		itemService.saveItem(itemSaveRequestDto);
		return "success";
	}

	// update
	@PutMapping("/{itemIdx}")
	public String update(@PathVariable int itemIdx, @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
		itemService.update(itemIdx, itemUpdateRequestDto);
		return "success";
	}
	

	// delete
	@DeleteMapping("/{itemIdx}")
	public String delete(@PathVariable int itemIdx) {
		itemService.delete(itemIdx);
		return "success";
	}
}
