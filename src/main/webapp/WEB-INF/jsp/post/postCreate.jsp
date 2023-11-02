<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-75">
		<h1>글 쓰기</h1>
		
		<input id="subject" type="text" class="form-control" placeholder="제목을 입력하세요.">
		<textarea id="content" class="form-control" placeholder="내용을 입력하세요." rows="10"></textarea>
		
		<div class="d-flex justify-content-end my-3">
			<input type="file" id="file" accept=".jpg,.jpeg,.png,.gif">  <!-- accept => 사용자 지정 파일 설정 : 다른 파일 확장자 업로드 막지 못함. -->
		</div>
		
		<div class="d-flex justify-content-between">
		
			<%-- a태그 => 왼쪽하단에 주소가 뜬다, button => 왼쪽하단에 주소가 뜨지 않는다. --%>
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			<div class="d-flex justify-content-between">
			<%-- AJAX 통신일 때는 반드시 button 타입으로 지정한다. --%>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
				<button type="button" id="saveBtn"class="btn btn-primary ml-3">저장</button>
			</div>
		</div>
		
		
	</div>
</div>

<script>

	$(document).ready(function() {
		
		// 1. 목록버튼 클릭
		$("#postListBtn").on("click", function() {
			location.href="/post/post-list-view";
		});
		
		// 2. 모두지우기 버튼 클릭
		$("#clearBtn").on("click", function() {
			$("#content").val("");
			$("#subject").val("");
		});
		
		// 3. 저장 버튼 클릭
		$("#saveBtn").on("click", function() {
			let subject = $("#subject").val();
			let content = $("#content").val();
			let fileName = $("#file").val();    // C:\fakepath\novel.png    => 임시파일로 저장됨
			
			//alert(file);
			
			// val체크 : db에서 nullable 확인!!!!!!!!!!!!!!
			if(!subject) {
				alert("제목을 입력하세요.");
				return;
			}
			
			if(!content) {
				alert("내용을 입력하세요.");
				return;
			}
			
			// 파일이 업로드 된 경우에만 확장자 체크
			if (fileName) {
				// C:\fakepath\novel.png    => 임시파일로 저장됨
				// 확장자만 뽑은 후 소문자로 변경
				// "."으로 split
				
				let ext = fileName.split(".").pop().toLowerCase();
				
				if ($.inArray(ext, ['jpg','jpeg','png','gif']) == -1) {    // 일치하면 인덱스 반환 but 없으면 -1 리턴
					alert("이미지 파일만 업로드 할 수 있습니다.");
					$("#file").val("");       // 파일 태그에 파일 제거 (보이지 않지만 업로드 될 수 있으므로)
					$("#fileName").text("");    // 파일명 비우기
					return;
				}
				
				
				
				//alert(ext);
				
				
				
			}
			
			// request param 구성
			// 파일은 무조건 form태그밖에는 이용 못 한다.
			// 이미지를 업로드 할때는 반드시 form 태그가 있어야 한다.      객체를 직접만들어서 사용
			let formData = new FormData();   // form태그 생성
			formData.append("subject", subject);  // key : form태그의 name속성과 같고 Request parameter명이 된다.
			formData.append("content", content);
			formData.append("file", $("#file")[0].files[0])   // 실제 파일을 보냄       문법 외우자!!!!!!!
			
			$.ajax({
				
				//request
				type:"POST"
				, url:"/post/create"
				, data:formData
				, enctype:"multipart/form-data"   // 약속된 정의어  =>  파일 업로드를 위한 필수 설정!!!!!!!!!!!
				, processData:false // 파일 업로드를 위한 필수 설정!!!!!!!!!!!
				, contentType:false // 파일 업로드를 위한 필수 설정!!!!!!!!!!!
				
				//response
				, success:function(data) {
					if (data.code == 200) {
						alert("저장되었습니다.");
						location.href="/post/post-list-view";
					} else {
						// 로직 실패 => 사용자 정보 없음 => 다시 로그인
						alert(data.errorMessage);
						location.href="/user/sign-in-view";
					}
				}
				, error(request, status, error) {
					alert("저장에 실패했습니다.");
				}
				
			});
			
		});
		
		
	});
	

	

</script>