package com.toy.todo.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.Users;

@Repository
public interface HomeRepository extends JpaRepository<Users, Long>{
}
