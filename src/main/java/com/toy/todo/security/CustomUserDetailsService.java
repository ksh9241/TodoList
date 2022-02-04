package com.toy.todo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.User;
import com.toy.todo.user.UserRepository;

/**
 * UserDetailsService 는 단일 사용자로 메모리 내 사용자 저장소를 설정합니다.
 * 시큐리티 설정에서 loginProcessingUrl("/login");
 * 요청이 들어오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행
 * */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(userName);
		User findUser = user.orElse(null);
		
		if (findUser == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new PrincipalDetails(findUser);
	}

}
