package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")            // json -> java obj
	public ResponseDto<Integer> save(@RequestBody User user) { 
		System.out.println("UserApiController : save 호출됨");
		//실제로 DB에  insert하기
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //ResponseEntity느낌
	}
	
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) { //HttpSession은 스프링컨테이너가 빈으로 갖고있다. 위에 쓰고 autowired하면 di됨.
		System.out.println("UserApiController : login 호출됨");
		User principal = userService.로그인(user); // principal : 접근주체
		if(principal!=null) {
			session.setAttribute("principal", principal); //세션이 생성된다.
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
