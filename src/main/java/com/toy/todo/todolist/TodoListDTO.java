package com.toy.todo.todolist;

import java.time.LocalDateTime;

import com.toy.todo.common.Paging;
import com.toy.todo.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoListDTO extends Paging{

	private Long todoIdx;
	private String todoContent;
	private LocalDateTime cretDt;
	private LocalDateTime chgDt;
	private String successYn;
	private User user;

}
