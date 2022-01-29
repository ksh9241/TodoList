package com.toy.todo.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	Integer save(UserDTO userDTO);
}
