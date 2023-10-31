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
					<td>${post.subject}</td>
					<td>
						<%-- ZonedDateTime > Date > String     jstl이 오래돼서 --%>
						
						<fmt:parseDate value="${post.createdAt}" pattern="yyyy-MM-dd" var="parsedCreatedAt" />
						<fmt:formatDate value="${parsedCreatedAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
					</td>
					<td>
						<%-- ZonedDateTime > Date > String     jstl이 오래돼서 --%>
						
						<fmt:parseDate value="${post.updatedAt}" pattern="yyyy-MM-dd" var="parsedUpdatedAt" />
						<fmt:formatDate value="${parsedUpdatedAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
					</td>
				</tr>
			</c:forEach>
			</tbody>		
		</table>
		<div class="d-flex justify-content-end">
			<a href="/post/post-create-view" class="btn btn-warning">글쓰기</a>
		</div>
	</div>
</div>