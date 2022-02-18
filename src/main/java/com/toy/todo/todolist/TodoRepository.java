package com.toy.todo.todolist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{

	List<TodoList> findByUserId(String userId); 
	
}
