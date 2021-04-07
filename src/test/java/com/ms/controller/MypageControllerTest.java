package com.ms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ms.domain.Image;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class MypageControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private MypageController mypageController;

	private User user;
	private Item testItem;
	private Image testImage;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageController).build();

		user = User.builder()
				.id("id2")
				.pw("pw2")
				.name("사용자아ㅏ")
				.email("test@daum.net")
				.phone("010-2344-2344")
				.build();

		testItem = Item.builder()
				.title("title")
				.content("content")
				.user(user)
				.charge("2000")
				.build();

		testImage = new Image();
		testImage.setItem(testItem);
		testImage.setImageUrl("271de8ef-1634-41fc-a3e9-cee51eaf838b_강아지.jpg");
		testImage.setImageOriName("강아지.jpg");

		userRepository.save(user);
		itemRepository.save(testItem);
		imageRepository.save(testImage);
	}

	@After
	public void tearDown() {
		imageRepository.deleteAll();
		itemRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Ignore
	@Test
	public void mypageMain_whenUserId_returnList() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/mypage")
				.param("userId", user.getId()))
				.andExpect(status().isOk());
				//.andExpect(jsonPath("$.evalList[0]"))
	}

	@Test
	public void saleList_userId_saleListOfUser() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/mypage/sale")
				.param("userId", user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].title").value(testItem.getTitle()));

	}
}
