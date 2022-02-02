package com.toy.todo.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeContorller {
	private ModelAndView mav = new ModelAndView();
	
	@GetMapping("/main")
	public ModelAndView homePage() {
		mav.setViewName("main");
		return mav;
	}
	
	@GetMapping("/")
	public ModelAndView mainPage() {
		mav.setViewName("main");
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage() {
		mav.setViewName("login");
		return mav;
	}
	
	@GetMapping("/signup")
	public ModelAndView signUpPage() {
		mav.setViewName("signup");
		return mav;
	}
}
