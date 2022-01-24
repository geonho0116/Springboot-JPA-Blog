package com.cos.blog.handler;
 
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice //어드바이스
@RestController
public class GlobalExceptionHandler {
	
	//@ExceptionHandler(value = Exception.class) 
	@ExceptionHandler(value = IllegalArgumentException.class) //해당 예외에 대한 핸들러
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	
	
}
