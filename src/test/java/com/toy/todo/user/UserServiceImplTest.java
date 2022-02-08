package com.toy.todo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.core.Is.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.toy.todo.domain.User;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserServiceImplTest {

	@Autowired
	UserService userService;
	
	@BeforeEach
	public void setUp() {
		userService = new UserServiceImpl();
	}
	
	@Test
	public void findByUsernameLike () {
		List<User> users = userService.findByUserNameContaining("ad");
		assertEquals(users.size(), is(0));
	}
}
