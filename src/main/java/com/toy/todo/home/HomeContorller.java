package com.toy.todo.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeContorller {
	
	@GetMapping("/")
	public String home() {
		return "Hello Test";
	}
}
