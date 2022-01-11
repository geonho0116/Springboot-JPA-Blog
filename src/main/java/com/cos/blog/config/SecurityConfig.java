package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//스프링 시큐리티 로그인페이지 커스터마이징을 위함

@Configuration //빈등록
@EnableWebSecurity //시큐리티 필터가 등록이 된다. 그 설정을 여기서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한/인증을 미리 체크하겠다는 뜻이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //리턴하는 값을 빈으로 등록한다. 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); //객체 리턴해서 암호화
	}
	
	// 시큐리티가 대신 로그인하는데 password를 가로채기 하는데 
	// 해당 password가 무엇으로 해쉬되어 회원가입 되었는지 알아야 같은 해쉬로 암호화해서 DB에 있는 해쉬와 비교한다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
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
				.loginPage("/auth/loginForm") //로그인페이지
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 오는 로그인을 가로채서 대신 로그인을 수행한다.
				.defaultSuccessUrl("/");
	}
}
