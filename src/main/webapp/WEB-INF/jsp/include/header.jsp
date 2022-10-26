<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    
<div class="header-box d-flex justify-content-between align-items-center">
	<!--logo  -->
	<div>
		<h1 class="font-weight-bold">MEMO 게시판</h1>
	</div>
	
	
	<!--로그인 정보  -->
	
	<div>
		<!-- 로그인이 되었을때만 정보노출 -->
		<c:if test="${not empty userName}">
		<span>${userLoginId}님 안녕하세요.</span>
		<a href="/user/sign_out/" class="font-weight-bold ml-3">로그아웃</a>
		</c:if>
	</div>
	
	
</div>