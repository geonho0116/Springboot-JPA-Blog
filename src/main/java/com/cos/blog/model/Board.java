package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터를 위한 어노테이션
	private String content; // 섬머노트 라이브러리를 사용할 예정이다. html태그가 섰여서 디자인됨 -> 용량이 커진다.
	
	@ColumnDefault("0") // 홑따옴표 없이 0을 넣는다.
	private int count; //조회수
	
	@JoinColumn(name="userId") //userId라는 필드가 생성되고
	@ManyToOne(fetch = FetchType.EAGER) // Many : Board , One : User 한명의 유저가 여러개의 게시글을 작성할 수 있다.
	private User user; //JPA ORM을 사용하면 자바 오브젝트를 사용하여 DB에 저장할 수 있다. => FK로 저장된다. 
	
	//mappedBy : 연관관계의 주인이 아니다.(나는 FK가 아니에요) -> DB에 컬럼을 만들지 마세요.라는 뜻
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)//EAGER : 꼭 들고와야하는 데이터들에 적용하는 전략이다. LAZY : DB에서 영속성 컨텍스트로 가져오지 않는다.
	private List<Reply> reply; 
	//여기에 넣어두면 JPA가 DB에서 정보를 가지고 올 때 자동으로 조인해서 reply의 정보까지 가져온다. 
	//테이블에까지 넣으려는게 아니라 join할 때 참고하기 위해 넣는 것이다.
	//오히려 DB에 넣어버리면 replyId 컬럼에 여러 값이 들어가기 때문에 제1정규형이 깨진다.
	
	
	@CreationTimestamp // 작성할 때의 시간이 들어간다. 
	private Timestamp createDate;
}
