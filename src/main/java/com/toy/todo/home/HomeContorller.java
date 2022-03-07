package com.toy.todo.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.domain.User;
import com.toy.todo.security.PrincipalDetails;
import com.toy.todo.todolist.TodoService;
import com.toy.todo.user.UserService;

@RestController
public class HomeContorller {
	private ModelAndView mav = new ModelAndView();
	
	@Autowired
	private UserService userService;
	
	// 테스트용
	@GetMapping("/main")
	public ModelAndView homePage() {
		mav.setViewName("test4");
		return mav;
	}
	
	// 메인페이지
	/* 로그인 했을 시 */
	@GetMapping("/auth")
	public ModelAndView mainPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		String userId = principalDetails.getUsername();
		
		// 로그인 정보를 얻기위해 조회를 해야함.
		User user = userService.findByUserId(userId);
		
		mav.addObject("findUser", user);
		mav.setViewName("main");
		
		return mav;
	}
	
	
	
	@GetMapping("/loginPage")
	public ModelAndView loginPage() {
		mav.setViewName("login");
		return mav;
	}
	
	@GetMapping("/joinPage")
	public ModelAndView signUpPage() {
		mav.setViewName("join");
		return mav;
	}
}
