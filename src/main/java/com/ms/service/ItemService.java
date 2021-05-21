package com.ms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.domain.Image;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ItemSaveRequestDto;
import com.ms.dto.ItemUpdateRequestDto;
import com.ms.interfaces.SearchItem;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ImageRepository imageRepository;

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
		item.setContent(itemSaveRequestDto.getContent());
		item.setCharge(itemSaveRequestDto.getCharge());
		item.setType(itemSaveRequestDto.isType());
		//item.setRegistrationDate(LocalDateTime.now());
		item.setRegistrationDate(itemSaveRequestDto.getRegistrationDate());
		if(itemSaveRequestDto.isType()) {
			item.setReturnDate(itemSaveRequestDto.getReturnDate());
		}

		return item;
	}

	public void uploadImages(List<MultipartFile> images, int itemIdx) throws IOException {
		/*
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		String folder = "/" + year + "/" + month + "/"; // '년/월'로 폴더 경로 지정
		*/
		String folder = "/home/ec2-user/uploads/";	//ec2 서버에서 권한문제로 home에 업로드해야됨
		System.out.println(folder);
		File newfile = new File(folder);

		if (!newfile.exists()) {// 폴더 있는지 검사
			try {
				newfile.mkdirs();// 없으면 저장된 경로로 폴더 생성
				System.out.println("폴더 생성");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더 있음");
		}

		if (imageRepository.findById(itemIdx) != null) {
			imageRepository.deleteByItem_ItemIdx(itemIdx);
		}

		for (MultipartFile file : images) {
			if (file.isEmpty()) {
				continue;
			}

			// 중복되지 않는 값을 사용해 파일명 설정
			UUID uid = UUID.randomUUID();
			String savedName = uid.toString() + "_" + file.getOriginalFilename();
			System.out.println(savedName);

			byte[] bytes = file.getBytes();
			Path path = Paths.get(folder + savedName);
			System.out.println("path: " + path);
			Files.write(path, bytes);	//여기서 에러(window O, linux X)

			Image i = new Image();
			i.setItem(new Item());
			i.getItem().setItemIdx(itemIdx);
			i.setImageOriName(file.getOriginalFilename());
			//i.setImageUrl(savedName);
			i.setImageUrl(path.toString());

			imageRepository.save(i);
		}
	}

	public byte[] getImage(String fileDir) throws IOException {
		FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            fis = new FileInputStream(fileDir);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fis.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch(IOException e){
            throw new RuntimeException("File Error");
        }
        return fileArray;
	}

	public List<SearchItem> findAll() {
		List<Item> itemList = itemRepository.findAll();

		List<SearchItem> searchItemList = new ArrayList<SearchItem>();

		if (itemList.isEmpty())
			return null;

		List<Integer> itemIdxList = new ArrayList();

		for(Item item: itemList) {
			itemIdxList.add(item.getItemIdx());
		}

		// 아이템 당 이미지 1개
		List<Image> imageList = imageRepository.findAllByItem_ItemIdxIn(itemIdxList);
		for(int i=0; i<itemList.size(); i++) {
			Item item = itemList.get(i);
			Image image = imageList.get(i);
			SearchItem si = new SearchItem(item);
			try {
				si.setImage(getImage(image.getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			searchItemList.add(si);
		}
		/*
		for (Item item : itemList) {
			SearchItem searchItem = new SearchItem(item);
			if (imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).size() != 0) {
				Image image = imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0);
				//searchItem.setImage(image);
				try {
					searchItem.setImage(getImage(image.getImageUrl()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			searchItemList.add(searchItem);

		}
		*/

		return searchItemList;

	}

	public List<Image> callImages(int itemIdx) {
		return imageRepository.findAllByItem_ItemIdx(itemIdx);
	}

	public Optional<Item> findById(Integer itemIdx) {
		return itemRepository.findById(itemIdx);
	}

	public Integer update(ItemUpdateRequestDto itemUpdateRequestDto) throws IOException {
		Item item = itemRepository.findById(itemUpdateRequestDto.getItemIdx()).orElseThrow(() -> new IllegalArgumentException("해당 아이템 없음"));

		item.setTitle(itemUpdateRequestDto.getTitle());
		item.setContent(itemUpdateRequestDto.getContent());
		item.setCharge(itemUpdateRequestDto.getCharge());
		item.setType(itemUpdateRequestDto.isType());
		item.setRegistrationDate(itemUpdateRequestDto.getRegistrationDate());
		if(itemUpdateRequestDto.isType()) {
			item.setReturnDate(itemUpdateRequestDto.getReturnDate());
		} else {
			item.setReturnDate(null);
		}

		itemRepository.save(item);

		return item.getItemIdx();
	}

	public Integer delete(int itemIdx) {
		if (imageRepository.findById(itemIdx) != null) {
			imageRepository.deleteByItem_ItemIdx(itemIdx);
		}
		itemRepository.deleteById(itemIdx);
		return itemIdx;
	}

	@Transactional
	public List<SearchItem> search(String keyword) {
		List<Item> itemList = itemRepository.findByTitleContaining(keyword);

		List<SearchItem> searchItemList = new ArrayList<SearchItem>();

		if (itemList.isEmpty())
			return null;

		List<Integer> itemIdxList = new ArrayList();

		for(Item item: itemList) {
			itemIdxList.add(item.getItemIdx());
		}

		List<Image> imageList = imageRepository.findAllByItem_ItemIdxIn(itemIdxList);
		for(int i=0; i<itemList.size(); i++) {
			Item item = itemList.get(i);
			Image image = imageList.get(i);
			SearchItem si = new SearchItem(item);
			try {
				si.setImage(getImage(image.getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			searchItemList.add(si);
		}

		/*
		for (Item item : itemList) {
			Image image = imageRepository.findAllByItem_ItemIdx(item.getItemIdx()).get(0);

			SearchItem searchItem = new SearchItem(item);
			//searchItem.setImage(image);
			try {
				searchItem.setImage(getImage(image.getImageUrl()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			searchItemList.add(searchItem);

		}
		*/

		return searchItemList;
	}

}
