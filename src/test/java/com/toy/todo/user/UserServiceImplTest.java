package com.toy.todo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.toy.todo.domain.User;

// Mockito 사용하려면 무조건 어노테이션 추가해야함.
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
	// 인터페이스로 진행할 경우 MockitoException 발생됨.
	@InjectMocks
	UserServiceImpl userService;
	
	// Spy는 Mock되지지 않는 메서드는 실제 메서드로 동작하는 어노테이션이다.
	@Spy
	BCryptPasswordEncoder passwordEncoder;
	
	private User user;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setIdx(999L);
		user.setUserId("Mockito");
		user.setUserName("모키토");
		user.setPassword("1");
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
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = encoder.encode(user.getPassword());
		user.setPassword(rawPassword);
		
		/** 
		 * userRepository.save = Mocking 할 메서드
		 * any(User.class) = 메서드의 파라미터
		 * willReturn(user) = 해당 메서드가 반환하는 값
		 */
		given(userRepository.save(any(User.class))).willReturn(user);
		
		//when
		User result = userRepository.save(user);
		
		//then
		assertEquals(user.getUserName(), result.getUserName());
		assertEquals(rawPassword, result.getPassword());
		
		// verify
		verify(userRepository).save(user);
	}
	
	@DisplayName("전체유저 조회")
	@Test
	public void findAll() {
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		
		final List<User> mock = new ArrayList<>();
		mock.add(user1);
		mock.add(user2);
		mock.add(user3);
		
		// given
		given(userRepository.findAll()).willReturn(mock);
		
		// when
		List<User> result = userRepository.findAll();
		
		// then
		assertEquals(result.size(), 3);
	}
	
	@DisplayName("특정 유저 조회")
	@Test
	public void findByUserId() {
		Optional<User> input = Optional.of(user);
		
		given(userRepository.findByUserId(any(String.class))).willReturn(input);
		
		Optional<User> optional = userRepository.findByUserId("Mockito");
		
		User result = optional.orElse(null);
		
		assertEquals(result.getUserName(), "모키토");
	}
}
