package com.toy.todo.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.User;
import com.toy.todo.user.UserContext;
import com.toy.todo.user.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(userId);
		User findUser = user.orElse(null);
		
		System.out.println("findUser == " + findUser);
		
		if (findUser == null) {
			throw new UsernameNotFoundException(userId);
		}
		List<GrantedAuthority> role = new ArrayList<>();
		role.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		UserContext userContext = new UserContext(findUser, role);
		
		return userContext;
	}

}
