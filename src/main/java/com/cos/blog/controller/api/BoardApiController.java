package com.cos.blog.controller.api;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.CommentSaveRequestDto;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")  
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) { 
		System.out.println("BoardApiController : save 호출됨");
		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //ResponseEntity느낌 -> 커스터마이징하려고 따로 구현함
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id,@AuthenticationPrincipal PrincipalDetail principal){
		System.out.println("BoardApiController : deleteById 호출됨");
		boardService.글삭제하기(id,principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@RequestBody Board board, @PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal){
		System.out.println("BoardApiController : update 호출됨");
		boardService.글수정하기(id,board,principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/board/{boardId}/reply")  
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) { 
		System.out.println("BoardApiController : replySave 호출됨");
		System.out.println(replySaveRequestDto);
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //ResponseEntity느낌 -> 커스터마이징하려고 따로 구현함
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PostMapping("/api/board/{boardId}/reply/{replyId}/comment")  
	public ResponseDto<Integer> commentSave(@RequestBody CommentSaveRequestDto commentSaveRequestDto) { 
		System.out.println("BoardApiController : commentSave 호출됨");
		System.out.println(commentSaveRequestDto); 
		boardService.대댓글쓰기(commentSaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //ResponseEntity느낌 -> 커스터마이징하려고 따로 구현함
	}
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}/{commentId}")
	public ResponseDto<Integer> commentDelete(@PathVariable int commentId){
		boardService.대댓글삭제(commentId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
