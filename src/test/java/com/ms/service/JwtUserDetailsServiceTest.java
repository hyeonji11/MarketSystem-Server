package com.ms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ms.domain.User;
import com.ms.dto.UserDto;
import com.ms.repository.UserRepository;

public class JwtUserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private JwtUserDetailsService jwtUserDetailsService;

	private User testUser;

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
	public void save_userInfo_success() {
		//given
		UserDto userDto = new UserDto();
		userDto.setId("id1");
		userDto.setPw("pw1");
		userDto.setName("사용자");
		userDto.setEmail("test1111@naver.com");
		userDto.setPhone("010-1111-1111");

		Mockito.when(userRepository.save(any(User.class)))
			.thenReturn(testUser);

		//when
		String userId = jwtUserDetailsService.save(userDto);

		//then
		verify(userRepository).save(any(User.class));
		assertThat(userId).isEqualTo("id1");
	}

	@Test
	public void loadUserByUsername_userId_ReturnUser() {
		//given
		Optional<User> user = Optional.of(testUser);

		Mockito.when(userRepository.findById("id1"))
				.thenReturn(user);

		//when
		User test = jwtUserDetailsService.loadUserByUsername("id1");

		//then
		verify(userRepository).findById("id1");
		assertThat(test).isEqualTo(testUser);
	}

}
