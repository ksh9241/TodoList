package com.toy.todo.user;

import java.time.LocalDateTime;
import java.util.List;

import com.toy.todo.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

	private Long userId;
	
	private String userName;
	
	private String password;
	
	private String phoneNumber;
	
	private LocalDateTime cretDt;
	
	private LocalDateTime chgDt;
	
	private Double achievementRate;
	
	private List<Board> boards;
}
