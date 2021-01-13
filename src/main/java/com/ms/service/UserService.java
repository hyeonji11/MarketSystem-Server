package com.ms.service;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.domain.User;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	/*
	@Autowired
	UserRepository userRepository;
	*/

	private final UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	public JavaMailSender emailSender;

	public List getUser() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Transactional
	public User signUpUser(User user) {
		user.setPw(passwordEncoder.encode(user.getPw()));
		return userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public String findId(String email, String name) {
		User user = userRepository.findByEmailAndName(email, name);
		return user.getId();
	}

	public boolean findPw(String id, String email) {
		User user = userRepository.findByIdAndEmail(id, email);

		if(user.getId() != null) {
			StringBuffer str = new StringBuffer();
			Random rnd = new Random();
			for (int i = 0; i < 10; i++) {
			    int rIndex = rnd.nextInt(3);
			    switch (rIndex) {
			    case 0:
			        // a-z
			    	str.append((char) ((rnd.nextInt(26)) + 97));
			        break;
			    case 1:
			        // A-Z
			    	str.append((char) ((rnd.nextInt(26)) + 65));
			        break;
			    case 2:
			        // 0-9
			    	str.append((rnd.nextInt(10)));
			        break;
			    }
			}

			sendMail(email, str.toString());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPw(encoder.encode(str.toString()));
			updateUser(user);
			return true;
		}

		return false;
	}

	public void sendMail(String email, String str) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setFrom("MarketSystem");
		msg.setSubject("[Market System] 비밀번호 찾기 메일");
		msg.setText("임시 비밀번호 : "+str);
		emailSender.send(msg);
	}
}
