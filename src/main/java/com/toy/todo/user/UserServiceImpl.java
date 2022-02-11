package com.toy.todo.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Integer save(User user) {
		try {
			String rawPassword = user.getPassword();
			String encPassword = passwordEncoder.encode(rawPassword);
			
			user.setRole("ROLE_USER");
			user.setPassword(encPassword);
			userRepository.save(user);
			
			return 1;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		
		return users;
	}

	@Override
	public User findByUserId(String userId) {
		Optional<User> optional = userRepository.findByUserId(userId);
		User user = optional.orElse(null);
		return user;
	}

	@Override
	public List<User> findByUserIdContaining(String userName) {
		List<User> users = userRepository.findByUserIdContaining(userName);
		return users;
	}
}
