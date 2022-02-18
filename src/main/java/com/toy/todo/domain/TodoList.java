package com.toy.todo.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "todolist")
public class TodoList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long TodoIdx;
	
	@NotNull
	private String TodoContent;
	
	@NotNull
	@CreationTimestamp // insert 시 값을 자동으로 채워줌
	private LocalDateTime cretDt;
	
	private LocalDateTime chgDt;
	
	private String successYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user; 
	
	
}
