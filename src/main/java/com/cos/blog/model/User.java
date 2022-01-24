package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @DynamicInsert //insert할 때 null인 컬럼(디폴트 값(여기서는 role컬럼)이 있기 때문에)을 제외해주는데 사용한다. 
@Entity // 클래스를 테이블화한다.  User클래스를 읽어서 자동으로 mysql에 테이블이 생성된다. 
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다는 의미.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 비밀번호를 해쉬하여 암호화할 것이기 때문에 100을 준다.
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	private String oauth; //kakao,google, ...
	
	@CreationTimestamp // 시간이 자동으로 입력된다. 
	private Timestamp createDate;
	
//	@ColumnDefault("'user'") // 문자라는 것을 알려주기 위해 내부에 'user'을 넣는다. => 이후에 set해주기로 함 @DynamicInsert하면 어노테이션 많아지니깐.
//	private String role; // Enum을 사용하는 것이 좋다. ex) admin, user, manager ... => 타입이 String이기 때문에 오타날 수 있음.
// DB에는 RoleType이라는 자료형이 없기 때문에 @Enumerated(EnumType.STRING)을 추가해준다. 
	@Enumerated(EnumType.STRING)
	private RoleType role;
}
