package com.cos.blog.controller.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

//	@Autowired
//	private HttpSession session;

	@PostMapping("/auth/joinProc") // json -> java obj
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // ResponseEntity느낌
	}

	@PutMapping("/user") // 회원정보수정
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.회원수정(user);
		// 여기는 트랜잭션이 종료되기 때문에 DB의 값은 변경이 됐다.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 로그아웃을 하고 다시 들어가야 회원정보가 수정된 것을 볼 수 있다.
		// (로그인하면 SecurityContextHolder 내의 SecurityContext의 Authentication이 생성된다)
		// (@AuthenticationPrincipal PrincipalDetails principal할 때 Authentication을 가져오는
		// 것임)

		// 세션등록
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	
//	전통적인 방식의 로그인(시큐리티를 사용하지 않음)
	// -> 로그인은 스프링 시큐리티가 가로채서 처리한다.
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) { //HttpSession은 스프링컨테이너가 빈으로 갖고있다. 위에 쓰고 autowired하면 di됨.
//		System.out.println("UserApiController : login 호출됨");
//		User principal = userService.로그인(user); // principal : 접근주체
//		if(principal!=null) {
//			session.setAttribute("principal", principal); //세션이 생성된다.
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}

}
