package com.ms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ms.config.JwtAuthenticationEntryPoint;
import com.ms.config.JwtTokenUtil;
import com.ms.service.JwtUserDetailsService;

@RunWith(SpringRunner.class)	//spring boot와 junit 사이 연결자
@WebMvcTest(HomeController.class)	//스프링 테스트 어노테이션 중 Web에 집중할 수 있는 어노테이션
public class HomeControllerTest {

	@Autowired
	private MockMvc mvc; //get, post 등의 API test 가능

	@MockBean
    private JwtUserDetailsService jwtUserDetailsService;

	@MockBean
    private JwtTokenUtil jwtTodenUtil;

	@MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Test
	public void returnHello() throws Exception {
		String hello = "hello";

		mvc.perform(get("/hello"))	// /hello 주소로 HTTP GET 요청
				.andExpect(status().isOk())	//status 검증. 여기선 200인지 검증
				.andExpect(content().string(hello));	//응답 본문 내용 검증. Controller에서 hello를 리턴하기 때문에 이 값이 맞는지 검증
	}

	@Test
	public void main_itemList() throws Exception {
		mvc.perform(get("/main"))
			.andExpect(status().isOk());
	}

}
