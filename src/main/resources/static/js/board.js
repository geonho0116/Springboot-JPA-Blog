let index = {
	init: function() {
		$("#btn-save").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.save();
			console.log("save클릭");
		});
		$("#btn-delete").on("click",()=>{ 
			this.deleteById(); //delete가 예약어임
			console.log("deleteById클릭");
		});
		$("#btn-update").on("click",()=>{ 
			this.update(); 
			console.log("update클릭");
		});
		$("#btn-reply-save").on("click",()=>{ 
			this.replySave(); 
			console.log("update클릭");
		});
		
	},

	save: function(){
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
	
	deleteById: function(){
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("삭제가 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){		
			//실패시
			//alert(JSON.stringify(error));
			//console.log(JSON.stringify(error))
			alert("삭제 권한이 없습니다.");
		}); 
	},
	
	update: function(){
		let id = $("#id").val();
	
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};
		
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("글수정이 완료되었습니다.");
			console.log(resp);
			location.href="/board/"+id;
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
	
	replySave: function(){
		let data = {
			userId:$("#userId").val(),
			boardId:$("#boardId").val(),
			content:$("#reply-content").val(),
		};
		
		console.log(data)
		
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("댓글작성이 완료되었습니다.");
			console.log(resp);
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
	replyDelete: function(boardId,replyId){
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("댓글삭제가 완료되었습니다.");
			console.log(resp);
			location.href = `/board/${boardId}`;
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
	},
	
	commentSave: function(boardId,replyId){
		let comment = prompt("대댓글을 입력해주세요");
		let data = {
			userId:$("#userId").val(),
			boardId:$("#boardId").val(),
			replyId:replyId,
			comment:comment,
		};
		console.log(data);
		$.ajax({
			type: "POST",
			url: `/api/board/${boardId}/reply/${replyId}/comment`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("대댓글 등록이 완료되었습니다.");
			console.log(resp);
			location.href = `/board/${boardId}`;
		}).fail(function(error){		
			//실패시
			//alert(JSON.stringify(error));
		}); 
	},
	commentDelete: function(boardId,replyId,commentId){
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}/${commentId}`,
			dataType: "json"
		}).done(function(resp){
			//성공시
			alert("대댓글삭제가 완료되었습니다.");
			console.log(resp);
			location.href = `/board/${boardId}`;
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
	},
}

index.init();