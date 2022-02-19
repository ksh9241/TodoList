package com.toy.todo.todolist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{

	//@Query("")
	List<TodoList> findAllByUserIdx(TodoListDTO todoDto);
	
}
