package com.toy.todo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<UserDTO> findByUserId(String userId);
}
