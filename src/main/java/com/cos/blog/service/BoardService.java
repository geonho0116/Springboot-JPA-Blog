package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	@Transactional //하나의 트랜잭션으로 묶여서 실행된다. 실행완료 -> commit, 실패 -> rollback
	public void 글쓰기(Board board,User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		System.out.println("BoardService : save 호출됨");
	}
	
	
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
		
	}
	
	
}
