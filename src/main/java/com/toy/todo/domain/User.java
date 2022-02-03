package com.toy.todo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
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
	
	private Double achievementRate;
	
	private String role;
	
	@OneToMany(mappedBy = "users")
	private List<Board> boards = new ArrayList<>();
}
