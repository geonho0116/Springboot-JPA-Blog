package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //어드바이스
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class) 
//	@ExceptionHandler(value = IllegalArgumentException.class) //해당 예외에 대한 핸들러
	public String handleArgumentException(IllegalArgumentException e) {
		
		return "<h1>"+e.getMessage()+"</h1>";
	}
}
