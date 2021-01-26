package com.ms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.ms.domain.User;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class MypageControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MypageController mypageController;

	private User user;

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

		userRepository.save(user);
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void mypageMain_whenUserId_returnList() throws Exception {
		//given

		//when&then
		mockMvc.perform(get("/mypage")
				.param("id", user.getId()))
				.andExpect(status().isOk());
				//.andExpect(jsonPath("$.evalList[0]"))
	}
}
