package com.toy.todo.todolist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{
	
	Page<TodoList> findAllByUserIdx(Long userIdx, Pageable pageable);
	
}
