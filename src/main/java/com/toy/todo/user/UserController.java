package com.toy.todo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping ("/user/findByUserId")
	public Map<String, UserDTO> findByUserId(@RequestParam("userId") String userId) {
		System.out.println("userId == " + userId);
		Map<String, UserDTO> map = new HashMap<>();
		
		//UserDTO user = (UserDTO)userService.loadUserByUsername("test");
		UserDTO user = new UserDTO();
		user.setUserId("test");
		map.put("userId", user);
		return map;
	}
}
