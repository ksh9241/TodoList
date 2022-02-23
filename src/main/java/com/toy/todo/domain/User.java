package com.toy.todo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Users")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idx")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idx;
	
	@NotNull
	@Column(unique=true)
	private String userId;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String password;
	
	private String phoneNumber;
	
	@NotNull
	@CreationTimestamp // insert 시 값을 자동으로 채워줌
	private LocalDateTime cretDt;
	
	@UpdateTimestamp // Update 시 값을 자동으로 채워줌
	private LocalDateTime chgDt;
	
	@ColumnDefault("0")
	private Double achievementRate;
	
	private String role;
	
	/**
	 * cascade = ALL : Entity의 상태 변화가 있다면 연관관계 엔티티도 상태 변화를 전이시키는 옵션이다.
	 * */
	@OneToMany(mappedBy = "user")
	private List<Board> boards = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<TodoList> todoList = new ArrayList<>();
}
