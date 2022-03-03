package com.toy.todo.todolist;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.todo.domain.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{
	
	@Query(value = "SELECT A.*"
			+ "		  FROM Todolist A"
			+ "		 WHERE user_idx = :userIdx"
			+ "		   AND TRUNC(cret_dt) = :findDate"
			, countQuery = "SELECT count(*) FROM Todolist WHERE TRUNC(cret_dt) = :findDate"
			, nativeQuery = true)	
	Page<TodoList> findAllByUserIdx(@Param("userIdx") Long userIdx, Pageable pageable, @Param("findDate") String findDate);

	/**
	 * clearAutomatically : persistent context의 캐시를 지워주는 옵션이다.
	 * flushAutomatically : persistent context 상태라면 flush 해서 update 해주겠다는 옵션이다.
	 * */
	@Transactional
	@Modifying
	@Query(value = "UPDATE Todolist t set t.success_yn = :sucesYn WHERE t.todo_idx = :todoIdx", nativeQuery = true)
	int updateSuccessYn(@Param("todoIdx") String todoIdx, @Param("sucesYn") String sucesYn);

	@Query(value = "SELECT COUNT(*) FROM Todolist t WHERE t.success_yn = 'Y' AND t.user_idx = :userIdx AND TRUNC(cret_dt) = :findDate", nativeQuery = true)
	int findSuccessCount(@Param("userIdx") Long userIdx, @Param("findDate") String findDate);
	
}
