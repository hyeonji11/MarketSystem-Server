package com.ms.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.Item;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.interfaces.ProjectItem;
import com.ms.interfaces.SearchItem;
import com.ms.service.ItemService;
import com.ms.service.ReportService;
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
	@Autowired
	ReportService reportService;

	// read list
	@GetMapping("/list")
	public List<SearchItem> item() {

		return itemService.findAll();
	}

	// read one
	@GetMapping()
	public ProjectItem item(@RequestParam(value = "itemIdx") int itemIdx) {
		Item item = itemService.findById(itemIdx).get();

		ProjectItem projectItem = new ProjectItem(item);
		projectItem.setImageList(itemService.callImages(itemIdx));

		return projectItem;
	}

	// search
	@GetMapping("/search")
	public List<SearchItem> search(@RequestParam(value = "keyword") String keyword) {

		return itemService.search(keyword);
	}

	// create
	@PostMapping("/save")
	public ResponseEntity<?> save(@ModelAttribute ItemSaveRequestDto itemSaveRequestDto) {
		int itemIdx = itemService.saveItem(itemSaveRequestDto);

		if (itemSaveRequestDto.getImages() != null) {
			try {
				itemService.uploadImages(Arrays.asList(itemSaveRequestDto.getImages()), itemIdx);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>(e.getStackTrace(), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
	}

	// update
	@PutMapping()
	public ResponseEntity<?> update(@RequestParam(value = "itemIdx") int itemIdx,
			@ModelAttribute ItemUpdateRequestDto itemUpdateRequestDto) throws IOException {
		itemService.update(itemIdx, itemUpdateRequestDto);

		if (itemUpdateRequestDto.getImages() != null) {
			try {
				itemService.uploadImages(Arrays.asList(itemUpdateRequestDto.getImages()), itemIdx);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
	}

	// delete
	@DeleteMapping()
	public String delete(@RequestParam(value = "itemIdx") int itemIdx) {
		itemService.delete(itemIdx);
		return "success";
	}


}
