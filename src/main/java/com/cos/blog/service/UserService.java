package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service //컴포넌트 스캔을 통한 bean 등록을 위함.(IoC)
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	
	@Transactional //하나의 트랜잭션으로 묶여서 실행된다. 실행완료 -> commit, 실패 -> rollback
	public void 회원가입(User user) {
		String rawPassword = user. getPassword(); //원문
		String encPassword = encoder.encode(rawPassword); //해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional //하나의 트랜잭션으로 묶여서 실행된다. 실행완료 -> commit, 실패 -> rollback
	public void 회원수정(User requestUser) {
		//수정시에는 영속성 컨텍스트에 User오브젝트를 영속화시킨 뒤에 영속화된 User오브젝트를 수정한다.
		User user = userRepository.findById(requestUser.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패: 해당하는 아이디가 없습니다.");
		});
		
		String rawPassword = requestUser. getPassword(); //변경할 비밀번호 원문
		String encPassword = encoder.encode(rawPassword); //변경할 비밀번호 해쉬
		user.setPassword(encPassword);
		user.setEmail(requestUser.getEmail());
		//서비스 종료시 트랜잭션이 종료되고 그때 영속성컨텍스트의 객체가 DB로 자동으로 커밋된다.

	}
	/* spring security를 사용할 것이기 때문에 주석처리함
	 * @Transactional(readOnly = true) // select인데 트랜잭션이 필요? 필요! select 시작~끝 동안에
	 * 정합성을 보장하기 위함. public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
	
}
