package com.toy.todo.user;

import java.util.List;

import com.toy.todo.domain.User;

public interface UserService {
	Integer save(User user);
	
	List<User> findAll();
	
	User findByUserName(String userName);
}
