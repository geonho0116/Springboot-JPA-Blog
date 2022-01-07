package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//빈으로 등록이 되나요? -> 자동으로 등록이 된다. 
//->@Repository 생략가능.
public interface UserRepository extends JpaRepository<User, Integer>{ //User테이블을 관리하고 PK는 integer형이야라는 뜻.

}
