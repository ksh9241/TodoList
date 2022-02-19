package com.toy.todo.todolist;

import org.mapstruct.Mapper;

import com.toy.todo.common.GenericMapper;
import com.toy.todo.domain.TodoList;

@Mapper(componentModel = "String")
public interface TodoMapper extends GenericMapper<TodoListDTO, TodoList>{

	@Override
	default TodoListDTO entityFromDto(TodoList entity) {
		if (entity == null) {
			return null;
		}
		
		TodoListDTO dto = new TodoListDTO(1);
		return null;
	}

	@Override
	default TodoList dtoFromEntity(TodoListDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void updateFromDto(TodoListDTO dto, TodoList entity) {
		// TODO Auto-generated method stub
		
	}

	
	
}
