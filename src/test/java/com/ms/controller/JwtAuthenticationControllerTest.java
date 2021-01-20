package com.ms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.domain.User;
import com.ms.dto.JwtRequest;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class JwtAuthenticationControllerTest {

	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtAuthenticationController jwtAuthenticationController;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationController).build();
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void login_idAndPw_returnToken() throws Exception {
		//given
		userRepository.save(User.builder()
				.id("id11")
				.pw("pw11")
				.email("test111@naver.com")
				.name("사용자11")
				.phone("010-3311-3311")
				.build());

		JwtRequest req = new JwtRequest("id11", "pw11");

		//when
		mvc.perform(post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(req)))
			.andExpect(status().isOk())
			.andExpect(content().string("아이디 혹은 이메일이 옳지 않습니다."));
	}

}
