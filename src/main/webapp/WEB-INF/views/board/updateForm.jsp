<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container" >
	<form>
		<input type="hidden" id="id" value="${board.id }"/>
		<div class="form-group">
			<label for="title">수정할 제목:</label> 
			<input value="${board.title}" type="text" class="form-control"  id="title"  >
			
		</div>
		<div class="form-group">
			<label for="content">수정할 내용:</label>
			<textarea class="form-control summernote" rows="5" id="content">${board.content} </textarea>
		</div>
	</form>
	<button id="btn-update" class="btn btn-primary">글 수정하기</button>
</div>
<script>
	$('.summernote').summernote(
			{
				tabsize : 2,
				height : 300,
				toolbar : [ [ 'style', [ 'style' ] ],
						[ 'font', [ 'bold', 'underline', 'clear' ] ],
						[ 'color', [ 'color' ] ],
						[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
						[ 'table', [ 'table' ] ],
						[ 'insert', [ 'link', 'picture', 'video' ] ],
						[ 'view', [ 'fullscreen', 'codeview', 'help' ] ] ]
			});
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>