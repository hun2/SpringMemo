<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 목록</h1>
		<table class="table">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성날짜</th>
					<th>수정날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${posts}" var="post">
					<tr>
						<td>${post.id}</td>
						<td>${post.content}</td>
						<td><fmt:formatDate value="${post.createdAt}" pattern="yyyy년 MM월 dd일"/></td>
						<td><fmt:formatDate value="${post.updatedAt}" pattern="yyyy년 MM월 dd일"/></td>
					</tr>
					
				</c:forEach>
			</tbody>
		</table>
		
		<div class="d-flex justify-content-end">
			<a href="/post/post_create_view" class="btn btn-success">글쓰기</a>
		</div>
	</div>
</div>