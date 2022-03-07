package com.toy.todo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.toy.todo.domain.User;

@WebMvcTest(controllers = UserService.class)
//@AutoConfigureWebMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
public class UserServiceImplTest {
	
	@MockBean
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	private User user;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setIdx(999L);
		user.setUserId("Mockito");
		user.setUserName("모키토");
		user.setPassword("1");
		user.setPhoneNumber("01012341234");
		user.setCretDt(LocalDateTime.now());
		user.setAchievementRate(0D);
		user.setRole("USER");
		
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
	}
	
	@DisplayName("유저 회원가입")
	@Test
	public void save() {
		// given
		given(userRepository.save(user)).willReturn(user);
		
		//when
		User result = userRepository.save(user);
		
		//then
		assertEquals(user.getUserName(), result.getUserName());
	}
}
