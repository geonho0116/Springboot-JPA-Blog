package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//빈으로 등록이 되나요? -> 자동으로 등록이 된다. 
//->@Repository 생략가능.
public interface UserRepository extends JpaRepository<User, Integer>{ //User테이블을 관리하고 PK는 integer형이야라는 뜻.
	//SELECT * FROM user WHERE username=?;
	//JPA 네이밍쿼리전략
	Optional<User> findByUsername(String username);
	
	
	
	
	//로그인을 위한 메서드 정의
	//JPA Naming쿼리 전략을 사용한다.
	//실제 JPA한테 없는 함수인데 이름을 이렇게 사용하면 
	//SELECT * FROM user WHERE username = ? AND password = ?; 쿼리가 실행된다...........
	//User findByUsernameAndPassword(String username, String password);
	//spring security를 사용하여 로그인 할 것이기에 주석처리함
	
	//네이티브쿼리 전략이다. 위에거랑 같은 건데 있어도 상관 없으니 주석처리는 안 함
	//@Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true) 
	//User login(String username, String password);
	//spring security를 사용하여 로그인 할 것이기에 주석처리함
}
