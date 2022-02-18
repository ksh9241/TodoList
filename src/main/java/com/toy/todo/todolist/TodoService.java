package com.toy.todo.todolist;

import java.util.List;

import com.toy.todo.domain.TodoList;

public interface TodoService {

	int save(TodoList todo);
	
	List<TodoList> findByUserId(String userId);

}
