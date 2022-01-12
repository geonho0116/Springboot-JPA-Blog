let index = {
	init: function() {
		$("#btn-save").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.save();
			console.log("save클릭");
		});
		$("#btn-delete").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.deleteById(); //delete가 예약어임
			console.log("deleteById클릭");
		});
		$("#btn-update").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.update(); //delete가 예약어임
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
			location.href="/";
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
}

index.init();