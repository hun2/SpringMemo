<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		<input type="text" class="form-control" placeholder="제목을 입력해주세요" id="subject" value="${post.subject}" >
   			<textarea class="form-control" rows="15" cols="100" id="content" >${post.content}</textarea>
   			<!--이미지가 있을 경우 이미지 미리보기 추가  -->
   			<c:if test="${not empty post.imagePath }">
   				<div class="mt-3">
					<img src="${post.imagePath}" width="200px" height="100px" >   				
   				
   				</div>
   			</c:if>
   			
   			
   			<div class="d-flex justify-content-end">
   				<input type ="file" id="file" accept=".jpg,.jpeg,.png,.gif" >
   			</div>
   			<div class="d-flex justify-content-between mt-3" >
   				<button type="button" class="btn btn-dark" id="postDeleteBtn" data-id="${post.id}">삭제</button>
   				<div class="d-flex justify-content-end">
   					<a href="/post/post_list_view" class="btn btn-info" >목록</a>
   					<button type="button" class="btn btn-secondary" id="postUpdateBtn" data-post-id="${post.id}">수정</button>
   				</div>
   			</div>
	</div>
</div>



<script>
	
	//수정버튼 클릭
	$('#postUpdateBtn').on("click", function(){
		
		var subject = $('#subject').val().trim();
		var content = $('#content').val();
		if (subject == '') {
			alert("제목을 입력하세요");
			return false;
		}
		
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
		
		
		// 폼태그 자바스크립트 작성
		var postId =  $(this).data('post-id');
		alert(postId);
		var formData = new FormData();
		formData.append("postId", postId);
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]);
	
		
		//ajax
		$.ajax({
			
			//request
			type: "PUT"
			,url : "/post/update"
			, data : formData
			, enctype : "multipart/form-data"  // 파일 업로드를 위한 필수 설정
			, processData : false // 파일 업로드를 위한 필수 설정
			, contentType : false
			
			//response
			,success : function(result){
				if(result.code ==100) {
					alert("수정되었습니다.");
					location.reload();
				} else {
					alert("result.eroorMessage");
				}
			}
			,error : function(e) {
				alert("관리자 문의")
			}
		})
		
		
	})
	
	
	
	//삭제버튼 클릭.
	$('#postDeleteBtn').on('click', function(){
		
		
		var postId =  $(this).data('id');
		$.ajax({
			type : "DELETE"
			, url : "/post/delete"
			, data : {"postId" : postId}
			, success : function(result) {
				if(result.code == 100) {
					alert("삭제 완료 하였습니다.");
					location.href = "/post/post_list_view";					
				} else {
					alert(result.errorMessage);
				}
				
			}
			,error : function(e) {
				alert("관리자에게 문의하세요");
			}
		})
	})
	
	
	
	
	


</script>