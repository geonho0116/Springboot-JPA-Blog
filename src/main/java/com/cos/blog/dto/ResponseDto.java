package com.cos.blog.dto;
 
//엔티티 안에 디티오 넣어서 안드로이드와도 통신할 수 있게끔 만들었다
//responseEntity만 사용하면 http만 왔다갔다해서 브라우저에만 적용할 수 있음.
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
	int status;
	T data;
}
