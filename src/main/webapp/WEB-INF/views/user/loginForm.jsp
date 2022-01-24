<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%> 
<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">이름:</label> <input type="text" name="username" class="form-control" placeholder="이름을 입력하세요" id="username">
		</div>

		<div class="form-group">
			<label for="password">비밀번호:</label> <input type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요" id="password">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=bc4856a1bacc998365f1426d964fc03e&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px"
			src="/image/kakao_login_button.png" /></a>
	</form>
</div>

<!-- <script src="/js/user.js"></script> 스프링 시큐리티 이용하여 로그인 할 것임-->

<%@ include file="../layout/footer.jsp"%>