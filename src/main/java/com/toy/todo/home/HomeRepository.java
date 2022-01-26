package com.toy.todo.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.UserVO;

@Repository
public interface HomeRepository extends JpaRepository<UserVO, Long>{
}
