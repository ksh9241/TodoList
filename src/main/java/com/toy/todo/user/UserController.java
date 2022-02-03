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
import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.domain.User;

@RestController
//@RequestMapping(value = "/user")
public class UserController {
	
	private ModelAndView mav; 
	
	@Autowired
	UserService userService;
	
	@PostMapping("/join")
	public ModelAndView join(User user) {
		int result = userService.save(user);
		mav = new ModelAndView();
		mav.setViewName("redirect:/joinPage");
		if (result > 0) {
			mav.setViewName("redirect:/loginPage");
		} 
		return mav;
	}

	@GetMapping ("/user/findByUserId")
	public Map<String, User> findByUserId(@RequestParam String userId) {
		System.out.println("userId == " + userId);
		Map<String, User> map = new HashMap<>();
		
		return map;
	}
	
	@GetMapping("/user/findAll")
	public Map<String, User> findAll() {
		List<User> users = userService.findAll();
		
		Map<String, User> map = users.stream().collect(Collectors.toMap(User::getUserName, Function.identity()));
		return map;
	}
}
