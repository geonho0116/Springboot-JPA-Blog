package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//스프링 시큐리티 로그인페이지 커스터마이징을 위함

@Configuration //빈등록
@EnableWebSecurity //시큐리티 필터가 등록이 된다. 그 설정을 여기서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한/인증을 미리 체크하겠다는 뜻이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean //리턴하는 값을 빈으로 등록한다. 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); //객체 리턴해서 암호화
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable() //csrf토큰 비활성화 -> 테스트할 때 해놓는게 좋음
			.authorizeRequests() //리퀘스트가 들어올 때 /auth/** 로 들어오는 애들 허용
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
				.permitAll()
				.anyRequest()
				.authenticated()//나머지는 인증이 돼야함.
			.and() //그리고
				.formLogin()
				.loginPage("/auth/loginForm"); //로그인페이지  
	}
}
