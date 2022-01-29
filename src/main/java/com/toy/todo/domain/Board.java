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
@Table(name = "Board")
public class Board {
	
	// 게시판 작성 내용
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long boardId;
	@NotNull
	private String subject;
	@NotNull
	private String content;
	@NotNull
	private LocalDateTime cretDt;
	private LocalDateTime chgDt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User users;

	// 댓글, 대댓글 기능
	private String comments;
	private Long commentDepth;
	private Long commentIdx;
	
	// 조회수
	private Long hit;
	
	
}
