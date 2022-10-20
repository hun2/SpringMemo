<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="sign-up-box">
		<h1 class="mb-4">회원가입</h1>
		<form id="signUpForm" method="post" action="/user/sign_up_for_submit">
			<table class="sign-up-table table table-bordered">
				<tr>
					<th>* 아이디(4자 이상)<br></th>
					<td>
						<%-- 인풋박스 옆에 중복확인을 붙이기 위해 div를 하나 더 만들고 d-flex --%>
						<div class="d-flex">
							<input type="text" id="loginId" name="loginId" class="form-control" placeholder="아이디를 입력하세요.">
							<button type="button" id="loginIdCheckBtn" class="btn btn-success">중복확인</button><br>
						</div>

						<%-- 아이디 체크 결과 --%>
						<%-- d-none 클래스: display none (보이지 않게) --%>
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
					</td>
				</tr>
				<tr>
					<th>* 비밀번호</th>
					<td><input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요."></td>
				</tr>
				<tr>
					<th>* 비밀번호 확인</th>
					<td><input type="password" id="confirmPassword" class="form-control" placeholder="비밀번호를 입력하세요."></td>
				</tr>
				<tr>
					<th>* 이름</th>
					<td><input type="text" id="name" name="name" class="form-control" placeholder="이름을 입력하세요."></td>
				</tr>
				<tr>
					<th>* 이메일</th>
					<td><input type="text" id="email" name="email" class="form-control" placeholder="이메일 주소를 입력하세요."></td>
				</tr>
			</table>
			<br>

			<button type="submit" id="signUpBtn" class="btn btn-primary float-right">회원가입</button>
		</form>
	</div>
</div>


<script>
	$(document).ready(function(){
		
		//중복확인
		$("#loginIdCheckBtn").on("click", function(){
			
			var loginId =  $("#loginId").val().trim();
			
			//id가 4자 이하인지 
			if (loginId.length < 4) {
				
				idCheckOk
				$("#idCheckLength").removeClass('d-none'); 
				$("#idCheckDuplicated").addClass('d-none');
				$("#idCheckOk").addClass('d-none');
				return false;
			}
			
			//ajax 중복 확인.
			
			$.ajax({
				
				type : "GET"
				, url : "/user/is_duplicated_id"
				, data : {"loginId": loginId}
				,success : function(data){
					if (data.result) {
						//중복일때
						$("#idCheckDuplicated").removeClass('d-none');
						$("#idCheckLength").addClass('d-none'); 
						$("#idCheckOk").addClass('d-none');
					} else {
						//아닐때
						$("#idCheckDuplicated").addClass('d-none');
						$("#idCheckLength").addClass('d-none'); 
						$("#idCheckOk").removeClass('d-none');
					}
				}
				, error : function(e) {
					alert("에러입니다."); 
				}
			});
			
			
			
		})
	//회원가입
	
	$("#signUpForm").on('submit', function(e){
		
		e.preventDefault(); //  submit 기능을 중단
			
		var loginId =  $("#loginId").val().trim();
		var password = $("#password").val().trim();
		var confirmPassword =  $("#confirmPassword").val().trim();
		var name = $("#name").val().trim();
		var email =$("#email").val().trim();
		
		if ( loginId.length == 0) {
			
			alert("아이디를 확인하세요");
			$("#loginId").focus();
			return false;
		}
		if (password.length == 0) {
			alert("비밀번호를 확인하세요");
			$("#password").focus();
			return false;
		}
		if (confirmPassword.length == 0) {
			alert("비밀번호를 확인하세요");
			$("#confirmPassword").focus();
			return false;
		}
		if (password != confirmPassword) {
			alert("비밀번호를 확인하세요");
			return false;
		}
		if (name.length == 0) {
			alert("이름을 확인하세요");
			$("#name").focus();
			return false;
		}
		if (email.length == 0) {
			alert("이메일을 확인하세요");
			$("#email").focus();
			return false;
		}
		
		//중복확인이 완료 되면 그때 submit 되게끔. $("#idCheckOk") => d-none이 없어야함.
		if ($("#idCheckOk").hasClass('d-none')) {
			alert("id 중복확인을 하세요")
			return false;
		} 
		
		
		
		// 1. submit 을 하는경우 => submit 이후로 다른 화면으로 넘길때 사용 (action 주소 => 뷰화면으로)
		//$(this)[0].submit();
		
		
		// 2. ajax 를 통한 방법
		//var url = $(this).attr('action');
		//var params =  $(this).serialize();      //name 속성에 있는 값들을 파라미터로 구성해준다. => 무조건 form 안에 있는것만 가능. 
		//console.log(params)
		
		
		
		// 3.다른 방법
		$.post(url, params)
		.done(function(data) {
			if (data.code == 100) {
				
				alert("가입을 환영합니다.");
				location.href = "/user/sign_in_view"
			} else {
				
				alert("가입에 실패하였습니다.");
				return false;
			}		
		});
		
		
	})
	
	
})
	
	
	

</script>