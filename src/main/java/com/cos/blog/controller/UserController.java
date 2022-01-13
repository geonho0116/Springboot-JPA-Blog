package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안 된 사용자들이 출입할 수 있는 경로 /auth/** 허용
// 그냥 주소가 / 면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {
	
	@Value("${kakaokey.key}")
	private String kakaokey;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		// post방식으로 key-value 데이터를 카카오쪽으로 요청해야한다.
		// http요청을 보내는 라이브러리. Retrofit2, OkHttp, RestTemplate 등이 있다.
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders(); // 헤더에 카카오에서 지정해준 것들을 보내기 위해 헤어 오브젝트 생성
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 헤더

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "bc4856a1bacc998365f1426d964fc03e"); // 날코딩 ㄴㄴ 변수화 후 사용해야해 나중엔
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// 헤더와 바디를 하나의 오브젝트에 담는다. 밑에 exchange라는 메서드가 HttpEntity를 받도록 되어있기 때문에
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청. POST방식, 응답을 response에 받는다.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class // 응답받을 클래스
		);

		// Json데이터를 자바 오브젝트로 담을 것. Gson, Json Simple, ObjectMapper 등을 사용한다.
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰 :" + oauthToken.getAccess_token());

		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders(); // 헤더에 카카오에서 지정해준 것들을 보내기 위해 헤어 오브젝트 생성
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 헤더
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token()); // 헤더

		// 헤더와 바디를 하나의 오브젝트에 담는다. 밑에 exchange라는 메서드가 HttpEntity를 받도록 되어있기 때문에
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// Http 요청. POST방식, 응답을 response에 받는다.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class // 응답받을 클래스
		);
		// System.out.println(response2);

		// Json데이터를 자바 오브젝트로 담을 것. Gson, Json Simple, ObjectMapper 등을 사용한다.
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// User -> username, password, email 필요하다

		System.out.println("카카오아이디(번호):" + kakaoProfile.getId());
		System.out.println("카카오이메일:" + kakaoProfile.getKakao_account().getEmail());
		System.out.println("게시판서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("게시판서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		//UUID garbagePassword = UUID.randomUUID(); // 카카오로그인이기때문에 따로 사용하진 않을 비밀번호임. 근데 다음에 로그인할 때 UUID는 매번 바뀌기 때문에 키 하나 만들어서 사용하기로 함
		System.out.println("게시판서버 패스워드 : " + kakaokey);

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(kakaokey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();

		// 가입자(이미 가입한 사람) 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if (originUser.getUsername() == null) { // 비가입자면
			userService.회원가입(kakaoUser);

		} 
		// 이미 가입한 사람이면 그냥 로그인 처리함
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaokey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
	}

	@GetMapping("user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
