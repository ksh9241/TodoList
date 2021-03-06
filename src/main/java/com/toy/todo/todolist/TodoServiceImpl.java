package com.toy.todo.todolist;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.TodoList;
import com.toy.todo.domain.User;
import com.toy.todo.security.PrincipalDetails;
import com.toy.todo.user.UserRepository;

@Service
public class TodoServiceImpl implements TodoService{

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String save(String todoContent, PrincipalDetails principalDetails) {
		TodoList todo = setUpSaveData(todoContent, principalDetails);
		
		Optional<User> optional = userRepository.findByUserId(todo.getUser().getUserId());
		User findUser = optional.orElseGet(null);
		Object result = null;
		
		if (findUser != null) {
			todo.setUser(findUser);
			result = todoRepository.save(todo);
		}
		
		return result != null ? "success" : "failed";
	}
	
	private TodoList setUpSaveData(String todoContent, PrincipalDetails principalDetails) {

		TodoList todo = new TodoList();
		User user = new User();
		user.setUserId(principalDetails.getUsername());
		todo.setTodoContent(todoContent);
		todo.setUser(user);
		return todo;
	}

	@Override
	public Map<String, Object> findAllByUserIdx(Long userIdx, Pageable pageable, String findDate) {
		
		Page<TodoList> result = todoRepository.findAllByUserIdx(userIdx, pageable, findDate);
		int successCount = successCount(userIdx, findDate);
		Map<String, Object> map = new HashMap<>(); 
		map.put("todoList", result);
		map.put("successCount", successCount);
		return map;
				
	}

	@Override
	public String updateSuccessYn(Map<String, String> map) {
		String todoIdx = map.get("idx");
		String sucesYn = map.get("successYn");
		
		int result = todoRepository.updateSuccessYn(todoIdx, sucesYn);
		
		return result > 0 ? "success" : "fail";
	}
	
	private int successCount(Long userIdx, String findDate) {
		int successCount = todoRepository.findSuccessCount(userIdx, findDate);		
		return successCount;
	}
}
