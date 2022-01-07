package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//사용자 요청에 대한 응답 -> HTML -> @Controller
//사용자 요청에 대한 Data를 응답
@RestController
public class HttpContollerTest {
	
	//브라우저요청은 get만 가능.
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get요청 : "+ m.getId();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter에 의해 자동매핑된다.
		return "post요청"+m.getId()+m.getUsername()+m.getPassword()+m.getEmail();
	}
	@PutMapping("/http/put")
	public String putTest() {
		return "put요청";
	}
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";
	}
}
