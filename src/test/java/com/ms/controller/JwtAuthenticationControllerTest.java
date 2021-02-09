package com.ms.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.ms.dto.JwtRequest;
import com.ms.dto.UserDto;
import com.ms.service.JwtUserDetailsService;
import com.ms.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class JwtAuthenticationControllerTest {

	private MockMvc mvc;

	@Autowired
	private JwtUserDetailsService jwtUserService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtAuthenticationController jwtAuthenticationController;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationController).build();
	}


	@Test
	public void login_idAndPw_returnToken() throws Exception {
		//given
		UserDto userDto = new UserDto();
		userDto.setId("id11");
		userDto.setPw("pw11");
		userDto.setName("사용자11");
		userDto.setEmail("test111@naver.com");
		userDto.setPhone("010-3311-3311");

		jwtUserService.save(userDto);

		JwtRequest req = new JwtRequest("id11", "pw11");

		//when
		mvc.perform(post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(req)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.jwtToken").value(any(String.class)))
			.andExpect(jsonPath("$.userIdx").value(any(Integer.class)))
			.andExpect(jsonPath("$.id").value("id11"));
			//.andExpect(content().string(any(String.class)));
	}

}
