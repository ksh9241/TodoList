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
public class UserVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String password;
	
	private String phoneNumber;
	
	@NotNull
	private LocalDateTime cretDt;
	
	private LocalDateTime chgDt;
	
	private Double achievementRate;
	
	@OneToMany(mappedBy = "users")
	private List<BoardVO> boards = new ArrayList<>();
}
