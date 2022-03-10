package com.toy.todo.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.toy.todo.domain.User;

//@WebMvcTest(controllers = UserController.class)
//@AutoConfigureWebMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@MockBean
	UserServiceImpl userService;
	
	@Autowired
	MockMvc mockMvc;
	
	private User user;
	
	private String userId;
	private Map<String,Boolean> map;
	
	@BeforeEach
	public void setUp() {
		userId = "1";
		map = new HashMap<String, Boolean>();
		map.put("result", true);
		
		user = new User();
		user.setIdx(999L);
		user.setUserId("Mockito");
		user.setUserName("모키토");
		user.setPassword("1");
		user.setPhoneNumber("01012341234");
		user.setCretDt(LocalDateTime.now());
		user.setAchievementRate(0D);
		user.setRole("USER");
	}
	
	@Test
	@DisplayName("아이디 중복체크")
	@Transactional
	public void 아이디중복체크 () throws Exception {
		final Map<String, Boolean> map = new HashMap<>();
		map.put("result", true);
		
		// given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메서드
		given(userService.checkUserId(userId)).willReturn(map);
		
		mockMvc.perform( get("/checkUserId"))
			.andExpect(status().isOk())
			.andDo(print());
		
		// verify : 해당 객체의 메서드가 실행되었는지 체크해줌
		verify(userService).checkUserId(userId);
	}
	
	@Test
	@DisplayName("회원가입")
	public void 회원가입() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", "회원가입에 성공하였습니다.");
		mav.addObject("url", "/loginPage");
		mav.setViewName("/message");
		
		given(userService.save(user)).willReturn(mav);
		
		ModelAndView result = userService.save(user);
		
		Gson gson = new Gson();
		String content = gson.toJson(result);
		
		mockMvc.perform( post("/join").content(content).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.msg").exists())
			.andExpect(jsonPath("$.url").exists())
			.andDo(print());
		
		verify(userService).save(user);
	}
}
