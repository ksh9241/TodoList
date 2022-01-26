package com.toy.todo.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.todo.domain.UserVO;

@RestController
public class HomeContorller {
	@Autowired
	HomeService homeService;
	
	@GetMapping("/")
	public String home() {
		List<UserVO> users = homeService.findUsers();
		
		return users.toString();
	}
}
