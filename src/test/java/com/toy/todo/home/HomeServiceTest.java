package com.toy.todo.home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.toy.todo.domain.Users;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class HomeServiceTest {
	
	private HomeRepository repository;
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;
	
	@BeforeEach
	void setUp () {
		emf = Persistence.createEntityManagerFactory("");
		
		em = emf.createEntityManager();
		
		tx = em.getTransaction();
	}
	
	@Test
	@Transactional
	void _회원가입() {
		tx.begin();
		
		try {
			Users user = new Users(1L,"Test", "1", "01012341234", LocalDateTime.now(), null, 0.0, null);
			em.persist(user);
			
			Users result = em.find(Users.class, user.getUserId());
			
			assertEquals(user.getUserId(), result.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
