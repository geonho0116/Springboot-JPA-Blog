<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id }" />
		<div class="form-group">
			<label for="username">이름:</label> <input type="text" value="${principal.user.username }" class="form-control" placeholder="이름을 입력하세요" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">수정할 비밀번호:</label> <input type="password" class="form-control" placeholder="비밀번호를 입력하세요" id="password">
			</div>
		</c:if>
		<c:choose>
			<c:when test="${empty principal.user.oauth }">
				<div class="form-group">
					<label for="email">수정할 이메일:</label> <input type="email" value="${principal.user.email }" class="form-control" placeholder="이메일을 입력하세요" id="email">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="email">이메일:</label> <input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email" readonly>
				</div>
			</c:otherwise>
		</c:choose>
	</form>
	<c:if test="${empty principal.user.oauth }"><button id="btn-update" class="btn btn-primary">회원정보수정</button></c:if>
</div>

<script src="/js/user.js"></script>
<!-- '/'하면 static을 찾아간다. -->


<%@ include file="../layout/footer.jsp"%>