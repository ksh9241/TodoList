package com.toy.todo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Users {
	
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
}
