package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ms.domain.User;
import com.ms.dto.UserDto;
import com.ms.repository.UserRepository;


public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User testUser;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
	}

	@Test
	public void getUser_returnList() {
		//given
		List<User> list = new ArrayList<User>();
		list.add(testUser);

		Mockito.when(userRepository.findAll())
				.thenReturn(list);

		//when
		List<User> users = userService.getUser();

		//then
		verify(userRepository, times(1)).findAll();
		assertThat(users).isEqualTo(list);
	}

//	@Test
//	public void updateUser_userInfo_save() {
//		//given
//		Mockito.when(userRepository.save(testUser))
//				.thenReturn(testUser);
//
//		//when
//		User user = userService.updateUser(testUser);
//
//		//then
//		verify(userRepository).save(testUser);
//		assertThat(passwordEncoder.matches("pw1", user.getPw())).isTrue();
//	}

	@Test
	public void findId_EmailAndName_True() {
		//given
		Mockito.when(userRepository.findByEmailAndName("test1111@naver.com", "사용자1"))
				.thenReturn(testUser);

		//when
		String userId = userService.findId("test1111@naver.com", "사용자1");

		//then
		assertThat(userId).isNotNull()
							.isEqualTo("id1");
	}

	@Test
	public void signUpUser_UserInfo_True() {

		//given
		Mockito.when(userRepository.save(testUser))
				.thenReturn(testUser);

		//when
		User user = userService.signUpUser(testUser);

		//then
		verify(userRepository, times(1)).save(testUser);
		assertThat(passwordEncoder.matches("pw1", user.getPw())).isTrue();
	}

	@Test
	public void getUserOne_userId_returnOneUser() {
		//given
		Optional<User> user = Optional.of(testUser);

		Mockito.when(userRepository.findById("id1"))
				.thenReturn(user);

		//when
		UserDto one = userService.getUserOne("id1");

		//then
		assertThat(one.getId()).isEqualTo(testUser.getId());
		assertThat(one.getEmail()).isEqualTo(testUser.getEmail());
	}

}
