package com.toy.todo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toy.todo.domain.User;
import com.toy.todo.security.UserDTO;

@RestController
//@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping ("/user/findByUserId")
	public Map<String, User> findByUserId(@RequestParam String userId) {
		System.out.println("userId == " + userId);
		Map<String, User> map = new HashMap<>();
		
		return map;
	}
	
	@GetMapping("/user/findAll")
	public Map<String, User> findAll() {
		List<User> users = userService.findAll();
		
		Map<String, User> map = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
		return map;
	}
	
	@PostMapping("/auth/checkUser")
	public String checkUser(@ModelAttribute UserDTO userDTO) {
		System.out.println("userDTO == " + userDTO);
		User user = userService.findByUserId(userDTO.getUserId());
		System.out.println(user);
		return null;
	}
}
