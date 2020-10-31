package com.ms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.domain.User;
import com.ms.dto.UserDto;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired UserRepository userRepository;

	@Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
	public User loadUserByUsername(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException((id)));
	}
	/**
	 * 회원정보 저장
	 *
	 * @param infoDto 회원정보가 들어있는 DTO
	 * @return 저장되는 회원의 PK
	 */
	@Transactional
	public String save(UserDto infoDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoDto.setPw(encoder.encode(infoDto.getPw()));

		return userRepository.save(User.builder()
				.id(infoDto.getId())
				.email(infoDto.getEmail())
				.name(infoDto.getName())
				.phone(infoDto.getPhone())
				.pw(infoDto.getPw()).build()).getId();
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		if ("user_id".equals(username)) {
//			return new User("user_id", "$2a$10$jCvWm3NXDRFs/EfuI4h4.u0ZxNocv.ZkgEy6qbjVXrfQ5.KzLfhAe",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//	}
}