package com.ms.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ms.domain.Evaluation;
import com.ms.domain.Image;
import com.ms.domain.Item;
import com.ms.domain.Transaction;
import com.ms.domain.User;
import com.ms.repository.EvaluationRepository;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.TransactionRepository;
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
	private TransactionRepository transRepository;

	@Autowired
	private EvaluationRepository evalRepository;

	@Autowired
	private MypageController mypageController;

	private User user2;
	private Item testItem;
	private Image testImage;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageController).build();

		user2 = User.builder()
				.id("id2")
				.pw("pw2")
				.name("사용자아ㅏ")
				.email("test@daum.net")
				.phone("010-2344-2344")
				.build();

		testItem = Item.builder()
				.title("title")
				.content("content")
				.user(user2)
				.charge("2000")
				.build();

		testImage = new Image();
		testImage.setItem(testItem);
		testImage.setImageUrl("271de8ef-1634-41fc-a3e9-cee51eaf838b_강아지.jpg");
		testImage.setImageOriName("강아지.jpg");

		userRepository.save(user2);
		itemRepository.save(testItem);
		imageRepository.save(testImage);
	}

	@After
	public void tearDown() {
		transRepository.deleteAll();
		imageRepository.deleteAll();
		itemRepository.deleteAll();
		evalRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void mypageMain_whenUserId_returnListDto() throws Exception {
		//given
		Transaction trans = Transaction.builder()
				.state("판매완료")
				.user(user2)
				.item(testItem)
				.build();

		Evaluation eval = Evaluation.builder()
				.user(user2)
				.rating(3)
				.review("좋음")
				.build();
		Evaluation eval2 = Evaluation.builder()
				.user(user2)
				.rating(4)
				.review("친절합니다")
				.build();
		Evaluation eval3 = Evaluation.builder()
				.user(user2)
				.rating(2)
				.review("약속 시간 늦음")
				.build();

		transRepository.save(trans);
		evalRepository.save(eval);
		evalRepository.save(eval2);
		evalRepository.save(eval3);

		//when&then
		mockMvc.perform(get("/mypage")
				.param("userId", user2.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.evalList.length()", is(2)))
				.andExpect(jsonPath("$.saleList.length()", is(1)))
				.andExpect(jsonPath("$.purchaseList.length()", is(1)));
	}

	@Test
	public void saleList_userId_saleListOfUser() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/mypage/sale")
				.param("userId", user2.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].title").value(testItem.getTitle()));

	}

	@Test
	public void purchaseList_userId_purchaseListOfUser() throws Exception {
		//given

		Transaction trans = Transaction.builder()
				.state("판매완료")
				.user(user2)
				.item(testItem)
				.build();

		transRepository.save(trans);

		//when&then
		mockMvc.perform(get("/mypage/purchase")
				.param("userId", user2.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].title").value(testItem.getTitle()));

	}
}
