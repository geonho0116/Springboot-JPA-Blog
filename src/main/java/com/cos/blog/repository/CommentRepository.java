package com.cos.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	//네이티브 쿼리. JPA가 해석하는 것이 아닌 내가 만드는 쿼리.(순서는 맞출 필요가 있다.) => 영속화 할 필요가 없다.
	@Modifying
	@Query(value = "INSERT INTO comment(userId,boardId,content,replyId,createDate) VALUES(?1,?2,?3,?4,now())",nativeQuery = true)
	int myCommentSave(int userId, int boardId, String content,int replyId);
}
