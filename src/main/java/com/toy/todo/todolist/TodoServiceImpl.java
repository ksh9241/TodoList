package com.toy.todo.todolist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private TodoMapper mapper = Mappers.getMapper(TodoMapper.class);
	
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
	public List<TodoListDTO> findAllByUserIdx(TodoListDTO todoDto) {
		List<TodoList> list = todoRepository.findAllByUserIdx(todoDto);
		
		List<TodoListDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			dtoList.add(mapper.entityFromDto(list.get(i)));
		}
		
		return dtoList;
	}

}
