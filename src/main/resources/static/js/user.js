let index = {
	init: function() {
		$("#btn-save").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.save();
			console.log("save클릭");
		});
		// $("#btn-login").on("click",()=>{ 
		// 	this.login();
		// 	console.log("save클릭");
		// });
		// 스프링시큐리티쓸거라주석
		
		$("#btn-update").on("click",()=>{ //function(){}, () =>를 사용하는 이유는 this를 바인딩하기 위함이다. function을 사용하면 윈도우객체를 가리킨다.
			this.update();
			console.log("update클릭");
		});
	},

	save: function(){
		// alert('user의 save함수 호출됨.')
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val(),
		};

		// console.log(data);

		//ajax호출시 default가 비동기 호출
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청하기
		$.ajax({
			//회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //http body 데이터의 타입(요청)
			contentType: "application/json; charset=utf-8",
			dataType: "json"//응답타입을 지정한다. default는 String인데 형태가 json이면 바꾼다.
		}).done(function(resp){
			//성공시
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
	
	// login: function(){
	// 	// alert('user의 save함수 호출됨.')
	// 	let data = {
	// 		username:$("#username").val(),
	// 		password:$("#password").val(),
	// 	};

	// 	$.ajax({
	// 		//회원가입 수행 요청
	// 		type: "POST",
	// 		url: "/api/user/login",
	// 		data: JSON.stringify(data), //http body 데이터의 타입(요청)
	// 		contentType: "application/json; charset=utf-8",
	// 		dataType: "json"//응답타입을 지정한다. default는 String인데 형태가 json이면 바꾼다.
	// 	}).done(function(resp){
	// 		//성공시
	// 		alert("로그인이 완료되었습니다.");
	// 		location.href="/";
	// 	}).fail(function(error){		
	// 		//실패시
	// 		alert(JSON.stringify(error));
	// 	});
	// } ,
	
	update: function(){
		
		
		let data = {
			id: $("#id").val(),
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val(),
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), //http body 데이터의 타입(요청)
			contentType: "application/json; charset=utf-8",
			dataType: "json"//응답타입을 지정한다. default는 String인데 형태가 json이면 바꾼다.
		}).done(function(resp){
			//성공시
			alert("회원수정이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){		
			//실패시
			alert(JSON.stringify(error));
		}); 
		
	},
}

index.init();