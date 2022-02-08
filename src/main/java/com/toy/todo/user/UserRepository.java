package com.toy.todo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.User;

/**
 * JPA Like 문법
 * ...Like : LIKE ':Data'
 * ...StartingWith : LIKE '%:Data'
 * ...EndingWith : LIKE ':Data%'
 * ...Containing : LIKE '%:Data%'
 * */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	// JPA Query Method 문법
	Optional<User> findByUserId(String userId);
	
	List<User> findByUserNameContaining(@Param("username") String userName);
}
