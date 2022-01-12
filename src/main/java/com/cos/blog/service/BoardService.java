package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.auth.PrincipalDetail;
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
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id,  PrincipalDetail principal) {
		Board board = boardRepository.findById(id)
		.orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
		});
		if(board.getUser().getId() != principal.getUser().getId()) {
			throw new IllegalStateException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
		}
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard, PrincipalDetail principal) {
		Board board = boardRepository.findById(id) //영속화를 먼저 한다. 그 이후에 수정할 값으로 수정하고 커넥션 끊으면 자동커밋된다.
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				});
		if(board.getUser().getId() != principal.getUser().getId()) { //권한검사
			throw new IllegalStateException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
		}
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(서비스가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 일어나서 자동업데이트가 된다.DB로 flush된다.
	}
}
