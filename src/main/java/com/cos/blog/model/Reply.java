package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {
	
	@Id//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@JoinColumn(name = "boardId")
	@ManyToOne //여러개의 답변이 하나의 게시글에 존재한다.
	private Board board;
	
	@JoinColumn(name = "userId")
	@ManyToOne //여러개의 답변을 하나의 유저가 작성 가능
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
