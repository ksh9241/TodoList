package com.toy.todo.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.User;
import com.toy.todo.security.UserDTO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Integer save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		
		return users;
	}
}
