package com.toy.todo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		Map<String, User> map = new HashMap<>();
		
		return map;
	}
	
	@Secured("ROLE_ADMIN")	// 특정 메서드에 호출 권한을 주고싶을 때
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")	// 권한 설정이 복수일 경우 @PreAuthorize 어노테이션을 활용한다.
	@GetMapping("/user/findAll")
	public Map<String, User> findAll() {
		List<User> users = userService.findAll();
		
		Map<String, User> map = users.stream().collect(Collectors.toMap(User::getUserName, Function.identity()));
		return map;
	}
	
	@PostMapping ("/user/findByUserName")
	public List<User> findByUserName(@RequestBody String username) {
		System.out.println("username == " + username);
		List<User> findUsers = userService.findByUserNameContaining(username);
		System.out.println(findUsers);
		
		return null;
	}
	
	@GetMapping ("/checkUserId")
	public Map<String, Boolean> checkUserId(@RequestParam String userId) {
		
		User checkUser = userService.findByUserId(userId);
		Map<String, Boolean> result = new HashMap<>();
		if (checkUser == null) {
			result.put("result", true);
		} 
		else {
			result.put("result", false);
		}
		return result;
	}
}
