package com.cos.blog.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	@Test
	public void 해쉬_암호화() {
		String encPassword = new BCryptPasswordEncoder().encode("12345");
		System.out.println(encPassword);
		

	}
}