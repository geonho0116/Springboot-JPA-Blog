package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	@GetMapping("/")
//	public String index(@AuthenticationPrincipal PrincipalDetail principal) { //컨트롤러에서 세션을 어떻게 찾지? jsp 에서는  jstl로 찾았는데
	public String index() {
		///WEB-INF/views/index.jsp
		return "index";
	}
	
	// USER 권한 필요
	@GetMapping("/board/saveForm")
	public String savaForm() {
		return "board/saveForm";
	}
}
