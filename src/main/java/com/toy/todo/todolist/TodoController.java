package com.toy.todo.todolist;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.toy.todo.security.PrincipalDetails;

@RestController
public class TodoController {
	
	private ModelAndView mav;
	
	@Autowired
	TodoService todoService;
	
	@GetMapping("addTodoPop")
	public ModelAndView openPopup() {
		mav = new ModelAndView();
		mav.setViewName("popup");
		
		return mav;
	}
	
	@PostMapping("todo/addTodo")
	public String addTodo(
			@RequestBody String todoContent
			, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		return todoService.save(todoContent, principalDetails);
	}
	
	@GetMapping("/auth/{userIdx}")
	public Map<String, Object> findTodoListByUserId(@PathVariable String userIdx
			, @PageableDefault(page = 0, size = 5) Pageable pageable
			, @RequestParam String findDate) {
		return todoService.findAllByUserIdx(Long.parseLong(userIdx), pageable, findDate);
	}
	
	@PostMapping("/auth/updateSuccessYn")
	public String updateTodoSuccessYn(@RequestBody Map<String, String> map) {
		return todoService.updateSuccessYn(map);
	}
	
}
