package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
	
	@Id//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;
	
	@Column(nullable = false, length = 200)
	private String content;
	 
//	@JsonBackReference
//	@JoinColumn(name = "boardId")
//	@ManyToOne //여러개의 대댓글이 하나의 게시글에 존재한다.
//	private Board board;

	private int boardId;
	
	@JsonBackReference
	@JoinColumn(name = "replyId")
	@ManyToOne //여러개의 대댓글이 하나의 reply에 존재한다.
	private Reply reply;
	
	@JoinColumn(name = "userId")
	@ManyToOne //여러개의 대댓글을 하나의 유저가 작성 가능
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
	
}
