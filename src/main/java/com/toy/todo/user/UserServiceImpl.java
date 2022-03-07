package com.toy.todo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.domain.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public ModelAndView save(User user) {
		try {
			/** 암호화, Role 세팅해주는 부분 메서드로 처리하는게 나을지 고민중  (살짝 지저분함) */
			String rawPassword = user.getPassword();
			String encPassword = passwordEncoder.encode(rawPassword);
			
			user.setRole("ROLE_USER");
			user.setPassword(encPassword);
			user.setAchievementRate(0d);
			
			
			User result = userRepository.save(user);
			
			ModelAndView mav = new ModelAndView();
			if (result != null) {
				mav.addObject("msg", "회원가입에 성공하였습니다.");
				mav.addObject("url", "/loginPage");
			}
			else {
				mav.addObject("msg", "회원가입에 실패하였습니다.");
				mav.addObject("url", "/joinPage");
			}
			
			mav.setViewName("/message");
			
			return mav;
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		
		return users;
	}

	@Override
	public User findByUserId(String userId) {
		Optional<User> optional = userRepository.findByUserId(userId);
		User user = optional.orElse(null);
		return user;
	}
	
	@Override
	public Map<String, Boolean> checkUserId(String userId) {
		Optional<User> optional = userRepository.findByUserId(userId);
		User user = optional.orElse(null);
		
		Map<String, Boolean> result = new HashMap<>();
		if (user == null) {
			result.put("result", true);
		} 
		else {
			result.put("result", false);
		}
		
		return result;
	}

	@Override
	public List<User> findByUserIdContaining(String userName) {
		List<User> users = userRepository.findByUserIdContaining(userName);
		return users;
	}

	
}
