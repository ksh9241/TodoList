package com.toy.todo.todolist;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.toy.todo.domain.TodoList;
import com.toy.todo.security.PrincipalDetails;

public interface TodoService{
	Map<String, Object> findAllByUserIdx(Long userIdx, Pageable pageable, String findDate);

	String updateSuccessYn(Map<String, String> map);

	String save(String todoContent, PrincipalDetails principalDetails);
}
