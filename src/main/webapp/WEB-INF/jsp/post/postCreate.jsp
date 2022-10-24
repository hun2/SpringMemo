<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <div class="d-flex justify-content-center">
   		<div class="w-50">
   			<h1>글쓰기</h1>
   			<input type="text" class="form-control" placeholder="제목을 입력해주세요" id="subject">
   			<textarea class="form-control" rows="15" cols="100" placeholder="내용을 입력하세요" id="content"></textarea>
   			<div class="d-flex justify-content-end">
   				<input type ="file" id="file" accept=".jpg,.jpeg,.png,.gif">
   			</div>
   			<div class="d-flex justify-content-between mt-3" >
   				<button type="button" class="btn btn-dark" id="postListBtn">목록</button>
   				<div class="d-flex justify-content-end">
   					<button type="button" class="btn btn-info" id="clearBtn">모두 지우기</button>
   					<button type="button" class="btn btn-secondary" id="postCreateBtn">저장</button>
   				</div>
   			</div>
   		</div>
   </div>
   
   
   <script>
   
   $(document).ready(function (){
	
	   //목록 버튼 클릭 => 글 목록 화면으로 이동
	   $("#postListBtn").on("click", function(){
		    location.href="/post/post_list_view";
	   })
	   
	   //모두지우기 버튼 클릭 => 글 모든 글 지우기
	   $("#clearBtn").on("click", function(){
		 	
		   $("#subject").val(''); // val() 안에 '' 으로 하게되면 값이 없어진다.
		   $("#content").val(''); // 위와 동일.
	   })
	   
	   //글저장
	   $("#postCreateBtn").on("click", function(){
		   
		   var subject = $("#subject").val().trim();
		    if (subject =="") {
				alert("제목을 입력하세요");   
				$("#subject").focus();
				return false;
		   }
		   
		   var content = $("#content").val();
		   var file = $("#file").val();      // 파일의 경로 스트링임 => 실제로 파일이 업로드 된게아님.
		   //파일이 업로드 된 경우 확장자 체크
		   if ( file != "") {
			   //확장자를 일단 찾는다. 
			   file.split('.').pop() // 잘려있는 배열중 가장 마지막 배열을 일컫는 용어 pop 이라고함.
			   //찾아낸 확장자를 소문자로 변경
			   var ext =  file.split('.').pop().toLowerCase();
			   
			   // 소문자 변경된 확장자를 해당 확장자가 있는지를 확인.
			   if ( $.inArray(ext, ['gif', 'jpg', 'jpeg', 'png']) == -1) { // 배열안에 포함된게 없다면 -1로 찍힘. 
				   alert("gif, jpg, jpeg, png 파일만 가능합니다");
			   		$('#file').val(''); // 업로드 된 파일을 비워준다. (파일명 지우는거임.)
				   return false;
			   } 
		   }
		   
		   //그림을  ajax 할때는 방식이 바뀐다.
		   //위에서 폼태그가 없지만, 자바스크립트안에서 폼태그를 만든다. ★폼태그를 위에서 만들어서 보내는법도 구글링해봐야함★
		   var formData =  new FormData();
		   formData.append('subject', subject);       // form 태그에 name으로 넣는것과 같다 (request param 을 구성중인거임.)
		   formData.append('content', content);        // subject 나 content 는 var로 선언해놨음 위에.
		   formData.append('file', $('#file')[0].files[0]); // file이 배열형식으로 가져오는데 첫번째 배열의 첫번째 파일이라고함;;; 찾아봐야할듯;
		   
		   // ajax 통신 formData에 있는 데이터 전송
		   // 이미지는 get으로 절대 보내지 못함.
		   $.ajax({
			   type : 'POST'
			   , url : '/post/create'
			   , data : formData
			   , enctype : "multipart/form-data"  // 파일 업로드를 위한 필수 설정
			   , processData : false // 파일 업로드를 위한 필수 설정
			   , contentType : false
			   
			   
			   // 요청이 성공하고 난 후 
			   ,success : function (result) {
				   if ( result.code == 100) {
					   alert("저장되었습니다");
					   location.href = "/post/post_list_view";
				   } else {
					   alert(data.errorMessage);
				   }
			   }
		   		, error : function(e) {
		   			
		   			alert("관리자에 문의하세요")
		   			
		   		}
		   	
		   })
		   
	   })
   })
   
   
   
   </script>