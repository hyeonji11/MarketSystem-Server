package com.ms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.Item;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.interfaces.ProjectItem;
import com.ms.service.ItemService;
import com.ms.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ItemController {

	@Autowired
	ItemService itemService;
	@Autowired
	UserService userService;

	// read list
	@GetMapping("/list")
	public List<ProjectItem> item() {
		List<Item> items = itemService.findAll();

		List<ProjectItem> newItems = new ArrayList<ProjectItem>();

		for (Item item : items) {
			newItems.add(new ProjectItem(item));
		}

		return newItems;
	}

	// read one
	@GetMapping("/{itemIdx}")
	public ProjectItem item(@PathVariable("itemIdx") int itemIdx) {
		Item item = itemService.findById(itemIdx).get();

		return new ProjectItem(item);
	}

	// create
	// @RequestMapping(value = "/save" , method = RequestMethod.POST, consumes = {"multipart/form-data" })
	@PostMapping("/save")
	public ResponseEntity<?> save(/*@RequestBody @RequestParam*/ @ModelAttribute ItemSaveRequestDto itemSaveRequestDto) {
		System.out.println("itemIdx : "+itemSaveRequestDto.getUserId());
		int itemIdx = itemService.saveItem(itemSaveRequestDto);
		System.out.println("itemIdx : "+itemIdx);
		
		if(itemSaveRequestDto.getImages()!=null) {
			try {
				itemService.uploadImages(Arrays.asList(itemSaveRequestDto.getImages()), itemIdx);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		System.out.println(itemSaveRequestDto.getTitle());
		return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
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
