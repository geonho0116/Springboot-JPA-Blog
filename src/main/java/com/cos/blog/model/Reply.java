package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@JsonBackReference
	@JoinColumn(name = "boardId")
	@ManyToOne (targetEntity = Board.class)//여러개의 답변이 하나의 게시글에 존재한다.
	private Board board;
	
	@JoinColumn(name = "userId")
	@ManyToOne //여러개의 답변을 하나의 유저가 작성 가능
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "reply", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) //Lazy로 해서 detail.jsp에서 바로 안 가져오게끔 했음 Lazy로 안하니까 board.replys에서 comments를 가져와버려서 for문이 댓글수*대댓글수만큼 돌아버림
	//@JsonIgnoreProperties({"reply"}) // board호출 -> reply호출 -> board호출 -> reply호출을 막아주기 위해 작성
	@OrderBy("id asc")
	private List<Comment> Comments; 
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
