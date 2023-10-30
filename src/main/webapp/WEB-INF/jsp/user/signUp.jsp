<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="sign-up-box">
		<h1 class="mb-4">회원가입</h1>
		<form id="signUpForm" method="post" action="/user/sign-up">
			<table class="sign-up-table table table-bordered">
				<tr>
					<th>* 아이디(4자 이상)<br></th>
					<td>
						<div class="d-flex">
							<input type="text" id="loginId" name="loginId" class="form-control col-9" placeholder="아이디를 입력하세요.">
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
					<td><input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="비밀번호를 입력하세요."></td>
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
	$(document).ready(function() {
		
		// 중복버튼 눌렀을 때
		$("#loginIdCheckBtn").on("click", function() {
			
			// 경고문구 초기화
			$("#idCheckLength").addClass("d-none");
			$("#idCheckDuplicated").addClass("d-none");
			$("#idCheckOk").addClass("d-none");
			
			let loginId = $("#loginId").val();
			
			if (loginId.length < 4) {
				$("#idCheckLength").removeClass("d-none");
				return;
			}
			
			// AJAX - 중복확인
			
			$.ajax({
				
				// request
				type:"POST"
				, url:"/user/is_duplicated_id"
				, data:{"loginId":loginId}
			
				// response
				, success:function(data) {
					// {"code":200, "isDuplicated" : true} // 중복이면 true
					
					if (data.isDuplicated == true) {
						$("#idCheckDuplicated").removeClass("d-none");
					} else {
						$("#idCheckOk").removeClass("d-none");
					}
					
				}
				
				, error:function(request, status, error) {
					alert("중복확인에 실패했습니다.")
				}
				
			});
			
			
			
		});
		
		// 회원가입 submit
		$('#signUpForm').on('submit', function(e) {
			e.preventDefault();   // 서브밋 기능 막음   :  화면 안넘어감
			
			// alert("클릭");
			
			// validation
			let loginId = $("#loginId").val().trim();
			let password = $("#password").val();
			let comfirmPassword = $("#confirmPassword").val();
			let name = $("#name").val().trim();
			let email = $("#email").val().trim();
			
			if (!loginId) {
				alert("아이디를 입력하세요");
				return false;
			}
			if (!password || !comfirmPassword) {
				alert("비밀번호를 입력하세요");
				return false;
			}
			
			if (password != comfirmPassword) {
				console.log(password);
				console.log(confirmPassword);
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			
			if (!name) {
				alert("이름을 입력하세요.");
				return false;
			}
			if (!email) {
				alert("이메일을 입력하세요");
				return false;
			}
			
			// 중복확인 후 사용 가능한지 확인 => idCheckOk가 d-none이 있을 때 얼럿 띄움
			
			if ($('#idCheckOk').hasClass('d-none')){
				alert("아이디 중복확인을 해주세요.")
				return false;
			}
			
			// 서버로 보내는 방법 두 가지
			// 1) Submit을 자바스크립트로 동작 시킴
			//$(this)[0].submit(); // 화면 이동이 반드시 된다. (jsp, redirect)
			
			// 2) ajax - 응답값이 json
			let url = $(this).attr('action');
	
			let params = $(this).serialize(); // 폼태그에 있는 name 속성- 값으로 파라미터 구성
			console.log(params);
			
			$.post(url, params)   //   request
			.done(function(data) {  // response
				// {"code" : 200, "result" : "성공"}
				if (data.code == 200) { 
					// 성공
					alert("가입을 환영합니다. 로그인을 해주세요")
					location.href = "/user/sign-in-view";   // 로그인 화면으로 이동
				} else {
					// 로직 실패
					alert(data.errorMessage);
				}
			});
			
		});
		
	});
</script>



