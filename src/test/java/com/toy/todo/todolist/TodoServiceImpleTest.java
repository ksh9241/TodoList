package com.toy.todo.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.toy.todo.domain.TodoList;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImpleTest {
	
	@Mock
	TodoRepository todoRepository;

	@Autowired
	TodoService todoService;
	
	
	public void setUp() {
		todoService = new TodoServiceImpl();
	}
	
	@DisplayName("유저idx를 통한 TodoList조회")
	@Test
	@Disabled
	public void findAllByUserIdxTest() {
		Long userIdx = 1L;
		Pageable pageable = PageRequest.of(0, 5);
		String findDate = "20220303";
		
		// Page 객체를 초기화 할 방법을 모르겠음.
		
		Page<TodoList> result = todoRepository.findAllByUserIdx(userIdx, pageable, findDate); 
		
		given(todoRepository.findAllByUserIdx(userIdx, pageable, findDate)).willReturn(result);
		
		Page<TodoList> check = todoRepository.findAllByUserIdx(userIdx, pageable, findDate);
		
		assertEquals(result.getTotalElements(), check.getTotalElements());
	}
	
	@DisplayName("업데이트 결과테스트")
	@Test
	public void updateSuccessYnTest() {
		given(todoRepository.updateSuccessYn("1", "Y")).willReturn(1);
		
		int result = todoRepository.updateSuccessYn("1", "Y");
		
		assertEquals(result, 1);
		
		verify(todoRepository).updateSuccessYn("1", "Y");
	}
}
