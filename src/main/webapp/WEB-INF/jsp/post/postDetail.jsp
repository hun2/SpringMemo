<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		<input type="text" class="form-control" placeholder="제목을 입력해주세요" id="subject" value="${post.subject}" readonly="readonly">
   			<textarea class="form-control" rows="15" cols="100" id="content" readonly="readonly">${post.content}</textarea>
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
   				<button type="button" class="btn btn-dark" id="postDeleteBtn">삭제</button>
   				<div class="d-flex justify-content-end">
   					<a href="/post/post_list_view" class="btn btn-info" >목록</a>
   					<button type="button" class="btn btn-secondary" id="postUpdateBtn">수정</button>
   				</div>
   			</div>
	</div>
</div>