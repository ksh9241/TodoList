package com.toy.todo.todolist;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toy.todo.domain.TodoList;
import com.toy.todo.domain.User;
import com.toy.todo.user.UserRepository;

@Service
public class TodoServiceImpl implements TodoService{

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public int save(TodoList todo) {
		Optional<User> optional = userRepository.findByUserId(todo.getUser().getUserId());
		User findUser = optional.orElseGet(null);
		Object result = null;
		
		if (findUser != null) {
			todo.setUser(findUser);
			result = todoRepository.save(todo);
		}
		
		return result != null ? 1 : 0;
	}

	@Override
	public Page<TodoList> findAllByUserIdx(Long userIdx, Pageable pageable) {
		
		Page<TodoList> result = todoRepository.findAllByUserIdx(userIdx, pageable);
		
		return result;
				
	}
}
