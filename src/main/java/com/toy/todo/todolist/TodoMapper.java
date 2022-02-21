package com.toy.todo.todolist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.toy.todo.common.GenericMapper;
import com.toy.todo.domain.TodoList;

@Mapper
public interface TodoMapper extends GenericMapper<TodoListDTO, TodoList>{

	@Mapping(target = "")
	default TodoList todoListDtoToEntity(TodoListDTO todoListDto) {
		if (todoListDto == null) {
			return null;
		}
		
		TodoList todoList = new TodoList();
		
		
		todoList.setTodoIdx(todoListDto.getTodoIdx());
		todoList.setTodoContent(todoListDto.getTodoContent());
		todoList.setCretDt(todoListDto.getCretDt());
		todoList.setChgDt(todoListDto.getChgDt());
		todoList.setSuccessYn(todoListDto.getSuccessYn());
		todoList.setUser(todoListDto.getUser());
		
		return todoList;
	};

	@Mapping(target = "")
	default TodoListDTO todoListToDto(TodoList todoList) {
		if (todoList == null) {
			return null;
		}
		
		TodoListDTO todoListDto = new TodoListDTO();
		
		
		todoListDto.setTodoIdx(todoList.getTodoIdx());
		todoListDto.setTodoContent(todoList.getTodoContent());
		todoListDto.setCretDt(todoList.getCretDt());
		todoListDto.setChgDt(todoList.getChgDt());
		todoListDto.setSuccessYn(todoList.getSuccessYn());
		todoListDto.setUser(todoList.getUser());
		
		return todoListDto;
	};

	

	
	
}
