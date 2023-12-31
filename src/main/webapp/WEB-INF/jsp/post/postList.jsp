<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="d-flex justify-content-center">
	<div class="w-75">
		<h1>글 목록</h1>
		<table class="table">
			<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>입력날짜</th>
					<th>수정날짜</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${postList}" var="post">
				<tr>
					<td>${post.id}</td>
					<td><a href="/post/post-detail-view?postId=${post.id}">${post.subject}</a></td>
					
					<td>
						<%-- ZonedDateTime > Date > String     jstl이 오래돼서 --%>
						
						<fmt:formatDate value="${post.createdAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
					</td>
					<td>
						<%-- ZonedDateTime > Date > String     jstl이 오래돼서 --%>
						
						<fmt:formatDate value="${post.updatedAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
					</td>
				</tr>
			</c:forEach>
			</tbody>		
		</table>
		<%-- paging --%>
		<div class="text-center">
			<a href="/post/post-list-view" class="mr-5">&lt;&lt; 이전</a>
			<a href="/post/post-list-view" value="${nextId}">다음 &gt;&gt;</a>
		</div>
		
		<div class="d-flex justify-content-end">
			<a href="/post/post-create-view" class="btn btn-warning">글쓰기</a>
		</div>
	</div>
</div>