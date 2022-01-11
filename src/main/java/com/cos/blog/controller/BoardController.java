package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	@GetMapping("/")
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { //컨트롤러에서 세션을 어떻게 찾지? jsp 에서는  jstl로 찾았는데
		System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
		///WEB-INF/views/index.jsp
		return "index";
	}
}
