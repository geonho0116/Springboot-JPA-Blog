package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //di
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제 완료";
		
	}
	
	
	//update : email, password를 수정할 것이다.
	//save함수는
	//1. id를 전달하지 않으면 insert
	//2. id를 전달하면 해당 id에 대한 데이터가 있으면 update
	//3. id를 전달하면 해당 id에 대한 데이터가 없으면 insert
	// => save를 호출하지 않아도 @Transactional 어노테이션을 추가하여 더티 체킹을 하여 update를 한다.
	@Transactional //함수 종료시에 자동 커밋된다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json -> java object . MessageConverter의 Jackson 라이브러리를 사용.
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		User user = userRepository.findById(id).orElseThrow(()->{ //DB에서 영속성컨텍스트로 가져옴(영속화).
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword()); //영속화 된 user객체의 값을 변경하고 그냥 종료해도 변경이 감지된다.(변경 감지 -> 더티체킹)
		user.setEmail(requestUser.getEmail()); // @Transactional로 commit이 일어나서 영속성컨텍스트의 user오브젝트가 DB로 업데이트된다.
//		requestUser.setId(id);
//		userRepository.save(user); //id(또는 다른 값)가 있으면 update를 해준다. 모든 값을 넣어줘야 하기 때문에 위와 같이 사용한다.(기존의 것을 find한 뒤에 값을 추가하고 다시 넣는다.)
		return user;
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	//페이징 : 한 페이지당 2건의 데이터를 리턴받아서 보여준다.
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Direction.DESC) Pageable pageable) {
		Page<User> pagingUser= userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return pagingUser; //페이징 정보들도 같이 보낸다.
		//retrun users;
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() { //Supplier인터페이스를 new 하기위해 익명객체를 생성한다.(인터페이스를 구현하기 위함)
//			@Override
//			public User get() {
//				return new User();
//			}
//		}); //findById는 optional을 반환한다. => null인지 아닌지 판단해야한다.
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		return user;
		
		//람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
//		});
//		return user;
	}
	
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) {
	public String join(User user) {
		System.out.println("id: "+user.getId());
		System.out.println("role: "+user.getRole());
		System.out.println("createDate: "+user.getCreateDate());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
