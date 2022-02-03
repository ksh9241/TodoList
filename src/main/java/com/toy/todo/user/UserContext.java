package com.toy.todo.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserContext extends User{
	
	private final com.toy.todo.domain.User user;

	public UserContext(com.toy.todo.domain.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword(), authorities);
		this.user = user;
	}
	
	public com.toy.todo.domain.User getUser() {
		return this.user;
	}

}
