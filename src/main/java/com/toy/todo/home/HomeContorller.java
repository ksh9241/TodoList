package com.toy.todo.home;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.domain.User;
import com.toy.todo.security.PrincipalDetails;
import com.toy.todo.user.UserService;

@RestController
public class HomeContorller {
	private ModelAndView mav = new ModelAndView();
	
	@Autowired
	private UserService userService;
	
	// 테스트용
	@GetMapping("/main")
	public ModelAndView homePage() {
		mav.setViewName("test");
		return mav;
	}
	
	// 메인페이지
	@GetMapping("/auth")
	public ModelAndView mainPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		String userId = principalDetails.getUsername();
		
		User user = userService.findByUserId(userId);
		
		mav.addObject("findUser", user);
		mav.setViewName("layout/main_layout");
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
