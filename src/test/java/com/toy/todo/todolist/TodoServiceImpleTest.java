package com.toy.todo.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.core.Is.*;

import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class TodoServiceImpleTest {

	@Autowired
	TodoService todoService;
	
	public void setUp() {
		todoService = new TodoServiceImpl();
	}
	
	@Test
	public void findAllByUserIdxTest() {
		Long userIdx = 1L;
		Pageable pageable = PageRequest.of(0, 5);
		String findDate = "20220303";
		Map<String, Object> map = todoService.findAllByUserIdx(userIdx, pageable, findDate);
		
		
		assertEquals(map.size(), 2);
	}
}
