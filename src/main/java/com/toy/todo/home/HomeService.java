package com.toy.todo.home;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.Users;

@Service
public class HomeService {

	@Autowired
	private HomeRepository homeRepository;
	
	public boolean createUser (Users user) {
		Optional<Users> u = Optional.ofNullable(homeRepository.save(user));
		
		return u.get().getUserId() > 0;
	}
}
