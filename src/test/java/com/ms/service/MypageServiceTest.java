package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ms.domain.Image;
import com.ms.domain.Item;
import com.ms.domain.Transaction;
import com.ms.domain.User;
import com.ms.interfaces.SearchItem;
import com.ms.repository.ImageRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.TransactionRepository;
import com.ms.repository.UserRepository;

public class MypageServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ImageRepository imageRepository;

	@Mock
	private TransactionRepository transRepository;

	@InjectMocks
	private MypageService mypageService;

	private User testUser;
	private Item testItem;
	private Image testImage;
	List<Item> itemList;
	List<Image> imageList;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		testUser = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("test1111@naver.com")
				.phone("010-1111-1111")
				.build();

		testItem = Item.builder()
				.title("title")
				.content("content")
				.user(testUser)
				.charge("2000")
				.build();

		testImage = new Image();
		testImage.setItem(testItem);
		testImage.setImageUrl("271de8ef-1634-41fc-a3e9-cee51eaf838b_강아지.jpg");
		testImage.setImageOriName("강아지.jpg");

		itemList = new ArrayList<>();
		itemList.add(testItem);

		imageList = new ArrayList<>();
		imageList.add(testImage);

	}

	@Test
	public void getSaleList_userId_itemList() {
		//given
		String userId = "id1";

		testUser.setUserIdx(1);

		Optional<User> testUser2 = Optional.of(testUser);
		Mockito.when(userRepository.findById(userId))
				.thenReturn(testUser2);

		Mockito.when(itemRepository.findAllByUser_UserIdx(testUser.getUserIdx()))
				.thenReturn(itemList);

		Mockito.when(imageRepository.findAllByItem_ItemIdx(any(Integer.class)))
				.thenReturn(imageList);

		//when
		List<SearchItem> list = mypageService.getSaleList(userId);

		//then
		assertThat(list.get(0).title).isEqualTo(testItem.getTitle());
		//assertThat(list.get(0).image).isEqualTo(testImage);
	}

	@Test
	public void getPurChaseList_userId_itemList() {
		//given
		User testUser2 = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("test1111@naver.com")
				.phone("010-1111-1111")
				.build();

		Transaction t = Transaction.builder()
				.item(testItem)
				.user(testUser2)
				.state("판매완료")
				.build();

		testItem.setItemIdx(1);
		testUser2.setUserIdx(2);

		List<Integer> itemIdxList = new ArrayList();
		itemIdxList.add(testItem.getItemIdx());

		Optional<User> user = Optional.of(testUser2);
		Mockito.when(userRepository.findById(testUser2.getId()))
				.thenReturn(user);

		Mockito.when(transRepository.findItemIdxByUserIdx(user.get().getUserIdx()))
				.thenReturn(itemIdxList);

		Mockito.when(itemRepository.findByItemIdxIn(itemIdxList))
				.thenReturn(itemList);

		Mockito.when(imageRepository.findAllByItem_ItemIdx(any(Integer.class)))
				.thenReturn(imageList);

		//when
		List<SearchItem> list = mypageService.getPurchaseList(testUser2.getId());

		//then
		assertThat(list.get(0).charge).isEqualTo(testItem.getCharge());
		assertThat(list.get(0).itemIdx).isEqualTo(testItem.getItemIdx());
	}

	@Test
	public void itemToSearchItem_itemList_searchItemList() {
		//given
		Mockito.when(imageRepository.findAllByItem_ItemIdx(any(Integer.class)))
				.thenReturn(imageList);

		//when
		List<SearchItem> siList = mypageService.itemToSearchItem(itemList);

		//then
		assertThat(siList.get(0).itemIdx).isEqualTo(testItem.getItemIdx());
		assertThat(siList.get(0).title).isEqualTo(testItem.getTitle());
	}

	@Test
	public void getMainSaleList_userId_threeSaleList() {
		//given
		String userId = "id1";

		testUser.setUserIdx(1);

		Optional<User> testUser2 = Optional.of(testUser);
		Mockito.when(userRepository.findById(userId))
				.thenReturn(testUser2);

		Mockito.when(itemRepository.findFirst3ByUser_UserIdxOrderByItemIdxDesc(testUser.getUserIdx()))
				.thenReturn(itemList);

		Mockito.when(imageRepository.findAllByItem_ItemIdx(any(Integer.class)))
				.thenReturn(imageList);

		//when
		List<SearchItem> list = mypageService.getMainSaleList(userId);

		//then
		assertThat(list.size()).isLessThan(4);
		assertThat(list.get(0).title).isEqualTo(testItem.getTitle());
		//assertThat(list.get(0).images).isEqualTo(testImage);

	}

	@Test
	public void getMainPurchaseList_userId_threePurchaseList() {
		//given
		User testUser2 = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("test1111@naver.com")
				.phone("010-1111-1111")
				.build();

		Transaction t = Transaction.builder()
				.item(testItem)
				.user(testUser2)
				.state("판매완료")
				.build();

		testItem.setItemIdx(1);
		testUser2.setUserIdx(2);
		List<Integer> itemIdxList = new ArrayList();
		itemIdxList.add(testItem.getItemIdx());

		Optional<User> user = Optional.of(testUser2);
		Mockito.when(userRepository.findById(testUser2.getId()))
				.thenReturn(user);

		Mockito.when(transRepository.findItemIdxByUserIdx(user.get().getUserIdx()))
				.thenReturn(itemIdxList);

		Mockito.when(itemRepository.findByItemIdxIn(itemIdxList))
				.thenReturn(itemList);

		Mockito.when(imageRepository.findAllByItem_ItemIdx(any(Integer.class)))
				.thenReturn(imageList);

		//when
		List<SearchItem> list = mypageService.getPurchaseList(testUser2.getId());

		//then
		verify(itemRepository, times(1)).findByItemIdxIn(itemIdxList);
		assertThat(list.get(0).charge).isEqualTo(testItem.getCharge());
		assertThat(list.get(0).itemIdx).isEqualTo(testItem.getItemIdx());

	}
}
