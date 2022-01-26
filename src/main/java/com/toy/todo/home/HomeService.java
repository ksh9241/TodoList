package com.toy.todo.home;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.UserVO;

@Service
public class HomeService {

	@Autowired
	private HomeRepository homeRepository;
	
	public boolean createUser (UserVO user) {
		Optional<UserVO> u = Optional.ofNullable(homeRepository.save(user));
		
		return u.get().getUserId() > 0;
	}
	
	public List<UserVO> findUsers () {
		return homeRepository.findAll();
	}
}
