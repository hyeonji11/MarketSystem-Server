package com.ms.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.domain.Image;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;



@Service
public class ItemService {

	@Autowired ItemRepository itemRepository;
	@Autowired UserRepository userRepository;
	@Autowired ImageRepository imageRepository;
	
	public int saveItem(ItemSaveRequestDto itemSaveRequestDto) {
		Item item = createItem(itemSaveRequestDto);
		itemRepository.save(item);
		return item.getItemIdx();
	}
	
	public Item createItem(ItemSaveRequestDto itemSaveRequestDto) {
		System.out.println(itemSaveRequestDto.getUserId());
		
		Item item = new Item();
		item.setTitle(itemSaveRequestDto.getTitle());
		
		Optional<User> one = userRepository.findById(itemSaveRequestDto.getUserId());
		System.out.println(one.isPresent());
		User user = one.get();
		
		item.setUser(user);
		
		//item.getUser().setId(itemSaveRequestDto.getUserId());
		//item.setUser(itemSaveRequestDto.getUserId());
		item.setContent(itemSaveRequestDto.getContent());
		item.setCharge(itemSaveRequestDto.getCharge());
		item.setType(itemSaveRequestDto.isType());
		item.setRegistrationDate(LocalDateTime.now());
		item.setReturnDate(LocalDate.now().plusDays(7));
		
		/*
		Optional<User> one = userRepository.findById(itemSaveRequestDto.getUserId());
		
	    User user = one.get();
			Item item = Item.builder()
				.title(itemSaveRequestDto.getTitle())
				.user(user)
				.content(itemSaveRequestDto.getContent())
				.charge(itemSaveRequestDto.getCharge())
				.type(itemSaveRequestDto.isType())
				.registrationDate(LocalDateTime.now())
				.returnDate(LocalDate.now().plusDays(7))
				// .image(image)
				.build();
		*/
		return item;
	}
	
	public void uploadImages(List<MultipartFile> images, int itemIdx) throws IOException {
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;

		String folder = "/"+year+"/"+month+"/"; // '년/월'로 폴더 경로 지정
		System.out.println(folder);
		File newfile = new File(folder);

		if(!newfile.exists()) {// 폴더 있는지 검사
			try {
				newfile.mkdirs();//없으면 저장된 경로로 폴더 생성
				System.out.println("폴더 생성");
			}
			catch(Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더 있음");
		}


		for(MultipartFile file : images) {
			if(file.isEmpty()) {
				continue;
			}

			//중복되지 않는 값을 사용해 파일명 설정
			UUID uid = UUID.randomUUID();
			String savedName = uid.toString()+"_"+file.getOriginalFilename();
			System.out.println(savedName);

			byte[] bytes = file.getBytes();
			//Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Path path = Paths.get(folder + savedName);
			System.out.println("path: "+path);
			Files.write(path, bytes);

			Image i = new Image();
			i.setItem(new Item());
			i.getItem().setItemIdx(itemIdx);
			i.setImageOriName(file.getOriginalFilename());
			i.setImageUrl(savedName);
			imageRepository.save(i); //DB에 파일 경로 및 이름 저장
		}
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	public Optional<Item> findById(Integer itemIdx) {
		return itemRepository.findById(itemIdx);
	}

	public Integer update(int itemIdx, ItemUpdateRequestDto itemUpdateRequestDto) {
		Item item = itemRepository.findById(itemIdx).orElseThrow(() -> new IllegalArgumentException("해당 아이템 없음"));
		
		
		item.setTitle(item.getTitle());
		item.setContent(item.getContent());
		item.setCharge(item.getCharge());
		item.setType(item.isType());
		item.setRegistrationDate(LocalDateTime.now());
		item.setReturnDate(LocalDate.now().plusDays(7));
		itemRepository.save(item);
		
		return itemIdx;
	}

	public Integer delete(int itemIdx) {
		itemRepository.deleteById(itemIdx);
		return itemIdx;
	}
}
