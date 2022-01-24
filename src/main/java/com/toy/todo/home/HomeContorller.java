package com.toy.todo.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeContorller {

	private ModelAndView mav;
	
	@GetMapping("/")
	public String home() {
		return "Hello Test";
	}
}
