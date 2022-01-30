package com.toy.todo.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<UserDTO> userEntityWrapper =  userRepository.findByUserId(userId);
		UserDTO findUser = userEntityWrapper.orElse(null);
		
		System.out.println("findUser == " + findUser);
		
		if (findUser == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		return findUser;
	}

	@Override
	public Integer save(UserDTO userDTO) {
		return null;
	}

}
