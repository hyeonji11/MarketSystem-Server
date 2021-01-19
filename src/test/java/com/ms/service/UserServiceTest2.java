package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.ms.domain.User;
import com.ms.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class UserServiceTest2 {

	private GreenMail testSmtp;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private UserService userService;

	public User testUser;

	@Before
	public void setUp() {
		testUser = User.builder()
				.id("id1")
				.pw("pw1")
				.name("사용자")
				.email("babl117@naver.com")
				.phone("010-1111-1111")
				.build();

		testSmtp = new GreenMail(ServerSetupTest.SMTP);
		testSmtp.start();

	}

	@After
	public void finish() {
		testSmtp.stop();
	}

	@Test
	@Transactional
	public void findPw_IdAndEmail_True() throws InterruptedException, MessagingException {
		//given
		Mockito.when(userRepository.findByIdAndEmail("id1", "babl117@naver.com"))
				.thenReturn(testUser);

		//when
		boolean isTrue = userService.findPw("id1", "babl117@naver.com");

		//then
		Message[] messages = testSmtp.getReceivedMessages();

		assertThat(messages.length).isEqualTo(1);
		assertThat(messages[0].getSubject()).isEqualTo("[Market System] 비밀번호 찾기 메일");
		verify(userRepository, times(1)).findByIdAndEmail(any(String.class), any(String.class));
		verify(userRepository, times(1)).save(any(User.class));
		assertThat(isTrue).isTrue();

	}

}
