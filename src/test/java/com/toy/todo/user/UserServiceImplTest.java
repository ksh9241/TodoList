package com.toy.todo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.toy.todo.domain.User;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
//	@InjectMocks
//	UserService userService;
	
	private User user;
	
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
