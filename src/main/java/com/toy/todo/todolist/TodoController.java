package com.toy.todo.todolist;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.toy.todo.domain.TodoList;
import com.toy.todo.domain.User;
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
		
		TodoList todo = new TodoList();
		User user = new User();
		user.setUserId(principalDetails.getUsername());
		todo.setTodoContent(todoContent);
		todo.setUser(user);
		
		return todoService.save(todo);
	}
	
	@GetMapping("/auth/{userIdx}")
	public Map<String, Object> findTodoListByUserId(@PathVariable String userIdx
			, @PageableDefault(page = 0, size = 5) Pageable pageable
			, @RequestParam String findDate) {
		
		Map<String, Object> map = todoService.findAllByUserIdx(Long.parseLong(userIdx), pageable, findDate);
		
		return map;
	}
	
	@PostMapping("/auth/updateSuccessYn")
	public String updateTodoSuccessYn(@RequestBody Map<String, String> map) {
		String result = todoService.updateSuccessYn(map);
		return result;
	}
	
}
