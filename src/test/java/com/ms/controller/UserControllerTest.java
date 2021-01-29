package com.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.domain.User;
import com.ms.dto.UserDto;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
//@TestPropertySource("classpath:application-mail.properties")
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserController userController;

	private UserDto userDto;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		userDto = new UserDto();
		userDto.setId("id2");
		userDto.setPw("pw2");
		userDto.setName("사용자222");
		userDto.setEmail("test2222@naver.com");
		userDto.setPhone("010-2222-1111");
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void signUp_userDto_returnSuccess() throws Exception {
		//given


		//when
		mockMvc.perform(post("/user/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userDto)))
				.andExpect(status().isOk());

		//then
		List<User> all = userRepository.findAll();
		assertThat(all.get(0).getId()).isEqualTo(userDto.getId());
		assertThat(all.get(0).getEmail()).isEqualTo(userDto.getEmail());
	}

	@Test
	public void findId_userDto_returnId() throws Exception {
		//given
		UserDto user = new UserDto();
		user.setName("사용자333");
		user.setEmail("test3333@naver.com");

		userRepository.save(User.builder()
								.id("id3")
								.pw("pw3")
								.email("test3333@naver.com")
								.name("사용자333")
								.phone("010-3333-3333")
								.build());

		ObjectMapper mapper = new ObjectMapper();
		String jsonUser = mapper.writeValueAsString(user);

		mockMvc.perform(post("/user/findid")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser))
				.andExpect(status().isOk())
				.andExpect(content().string("id3"));
	}

	@Test
	public void logout_returnString() throws Exception {
		mockMvc.perform(get("/user/logout/result"))
				.andExpect(status().isOk())
				.andExpect(content().string("logout success"));
	}

	@Ignore
	public void findPw_rightIdAndEmail_success() throws Exception {
		//application-mail.properties로 바꾸고 진행할 시 성공.
		//application-mail.properties는 gitignore 상태

		//given
		UserDto user = new UserDto();
		user.setId("id3");
		user.setEmail("test3333@naver.com");

		userRepository.save(User.builder()
				.id("id3")
				.pw("pw3")
				.email("test3333@naver.com")
				.name("사용자333")
				.phone("010-3333-3333")
				.build());

		//when&then
		mockMvc.perform(post("/user/findpw")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string("메일 전송 완료"));
	}

	@Test
	public void findPw_wrongIdAndEmail_fail() throws Exception {
		//given
		UserDto user = new UserDto();
		user.setId("id1");
		user.setEmail("test3333@naver.com");

		userRepository.save(User.builder()
				.id("id3")
				.pw("pw3")
				.email("test3333@naver.com")
				.name("사용자333")
				.phone("010-3333-3333")
				.build());

		//when&then
		mockMvc.perform(post("/user/findpw")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string("아이디 혹은 이메일이 옳지 않습니다."));
	}

	@Test
	public void getUser_whenRequest_returnUser() throws Exception {
		//given
		userController.signUp(userDto);

		//when&then
		mockMvc.perform(get("/user/update")
				.param("id", userDto.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userDto.getId()))
				.andExpect(jsonPath("$.pw").value(""))
				.andExpect(jsonPath("$.email").value(userDto.getEmail()));
	}

	@Test
	public void updateUser_whenRequest_userUpdate() throws Exception {
		//given
		String name = "사용자ㅏ아아아아아아";
		String pw = "password432408204";

		userController.signUp(userDto);

		userDto.setName(name);
		userDto.setPw(pw);

		//when&then
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		MvcResult result = mockMvc.perform(put("/user/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(userDto.getName()))
				.andReturn();

		String str = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(str, User.class);

		assertThat(passwordEncoder.matches(pw, user.getPw())).isTrue();
	}

}