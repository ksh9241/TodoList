package com.toy.todo.todolist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.toy.todo.domain.TodoList;

public interface TodoService{
	int save(TodoList todo);

	Page<TodoList> findAllByUserIdx(Long userIdx, Pageable pageable);
}
