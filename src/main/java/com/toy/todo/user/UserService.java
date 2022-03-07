package com.toy.todo.user;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.domain.User;

public interface UserService {
	ModelAndView save(User user);
	
	List<User> findAll();
	
	User findByUserId(String userId);
	
	List<User> findByUserIdContaining(String userName);

	Map<String, Boolean> checkUserId(String userId);
}
