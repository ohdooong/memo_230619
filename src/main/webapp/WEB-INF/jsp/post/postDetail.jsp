<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<div class="d-flex justify-content-center">
	<div class="w-75">
		<h1>글 상세</h1>
		
		<input id="subject" type="text" class="form-control" placeholder="제목을 입력하세요." value="${post.subject}">
		<textarea id="content" class="form-control" placeholder="내용을 입력하세요." rows="10" >${post.content}</textarea>
		
		<!-- 이미지가 있을때에만 이미지 영역 추가 -->
		<c:if test="${not empty post.imagePath}">
		<div class="my-4">
			<img src="${post.imagePath}" alt="업로드 된 이미지" width="300">			
		</div>
		</c:if>
		
		<div class="d-flex justify-content-end my-3">
			<input type="file" id="file" accept=".jpg,.jpeg,.png,.gif">  <!-- accept => 사용자 지정 파일 설정 : 다른 파일 확장자 업로드 막지 못함. -->
		</div>
		
		<div class="d-flex justify-content-between">
		
			<%-- a태그 => 왼쪽하단에 주소가 뜬다, button => 왼쪽하단에 주소가 뜨지 않는다. --%>
			<button type="button" id="deleteBtn" class="btn btn-secondary">삭제</button>
			<div class="d-flex justify-content-between">
			<%-- AJAX 통신일 때는 반드시 button 타입으로 지정한다. --%>
				<a href="/post/post-list-view" class="btn btn-dark">목록으로</a>
				<button type="button" id="updateBtn"class="btn btn-primary ml-3">수정</button>
			</div>
		</div>
	</div>
</div>














