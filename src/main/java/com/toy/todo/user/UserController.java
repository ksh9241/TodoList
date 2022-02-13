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
	
	// 회원가입
	@PostMapping("/join")
	public ModelAndView join(User user) {
		int result = userService.save(user);
		mav = new ModelAndView();
		
		if (result > 0) {
			mav.addObject("msg", "회원가입에 성공하였습니다.");
			mav.addObject("url", "/loginPage");
		} 
		else {
			mav.addObject("msg", "회원가입에 실패하였습니다.");
			mav.addObject("url", "/joinPage");
		}
		mav.setViewName("/message");
		return mav;
	}
	
	// 회원가입 시 아이디 중복체크
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

		
	/**
	 * 로그인 인증 이후 접근 가능 uri
	 * */
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
	
	@PostMapping ("/user/searchUsers")
	public List<User> searchUsers(@RequestBody String userId) {
		List<User> findUsers = userService.findByUserIdContaining(userId);
		System.out.println(findUsers);
		
		return findUsers;
	}
}
