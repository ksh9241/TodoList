package com.toy.todo.todolist;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.toy.todo.domain.TodoList;

public interface TodoService{
	String save(TodoList todo);

	Map<String, Object> findAllByUserIdx(Long userIdx, Pageable pageable, String findDate);

	String updateSuccessYn(Map<String, String> map);
}
