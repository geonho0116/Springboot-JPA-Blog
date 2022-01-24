package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveRequestDto {
	private int userId;
	private int boardId;
	private int replyId;
	private String comment;
}
