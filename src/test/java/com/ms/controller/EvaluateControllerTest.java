package com.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.ms.domain.Evaluation;
import com.ms.domain.User;
import com.ms.dto.EvalRequestDto;
import com.ms.repository.EvaluationRepository;
import com.ms.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class EvaluateControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EvaluationRepository evalRepository;

	@Autowired
	private EvaluateController evalController;

	User testUser;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(evalController).build();
		testUser = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("test1111@naver.com")
				.phone("010-1111-1111")
				.build();
		userRepository.save(testUser);
	}

	@After
	public void tearDown() {
		evalRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void insertEvaluation_evalRequestDto_save() throws Exception {
		//given
		EvalRequestDto dto = new EvalRequestDto();
		dto.setRating(3);
		dto.setReview("좋음");
		dto.setUserId("id1");

		//when
		mockMvc.perform(post("/eval")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)))
				.andExpect(status().isOk());

		//then
		List<Evaluation> all = evalRepository.findAll();
		assertThat(all.size()).isEqualTo(1);
		assertThat(all.get(0).getUser().getId()).isEqualTo(dto.getUserId());
	}

	@Test
	public void evaluationList_userId_listAndAvg() throws Exception {
		//given
		Evaluation eval = Evaluation.builder()
				.user(testUser)
				.rating(3)
				.review("좋음")
				.build();
		Evaluation eval2 = Evaluation.builder()
				.user(testUser)
				.rating(4)
				.review("친절합니다")
				.build();

		evalRepository.save(eval);
		evalRepository.save(eval2);

		//when&then
		mockMvc.perform(get("/eval/list")
				.param("userId", testUser.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.average").value(3.5))
				.andExpect(jsonPath("$.evalList[1].review").value(eval2.getReview()))
				.andExpect(jsonPath("$.evalList[0].rating").value(3));

	}

}
