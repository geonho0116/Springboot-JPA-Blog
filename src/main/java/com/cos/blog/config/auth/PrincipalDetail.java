package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

//  스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//  스프링 시큐리티의 고유한 세션저장소에 저장한다. 그때 저장되는 것이 PrincipalDetail이 저장된다.
public class PrincipalDetail implements UserDetails{
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	private User user; //composition

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다 (true : 만료 안 됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다 (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 비밀번호가 만료되지 않았는지 리턴한다(true: 만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)인지 리턴한다 (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 가진 권한 목록을 리턴한다. (권한이 여러개 있는 경우 루프를 돌면서 처리한다) 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new GrantedAuthority() {
			//람다식 표현 가능 : collectors.add(()->{ return "ROLE_"+user.getRole();});
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole(); //ROLE_USER 리턴
			}
		});
		
		
		
		
		return null;
	}
}
