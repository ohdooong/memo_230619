package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 아이디 중복 확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId (
			@RequestParam("loginId") String loginId) {
		
		// db조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답값 만들고 리턴
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		
		if (user == null) {
			// 중복 아님
			result.put("isDuplicated", false);
		} else {
			result.put("isDuplicated", true);
		}
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,   
			@RequestParam("password") String password,
			@RequestParam("name") String name, 
			@RequestParam("email") String email) {
		
		// password 해싱 - md5 알고리즘
		String hashedPassword = EncryptUtils.md5(password);
		
		
		// DB insert
		Integer id = userBO.addUser(loginId, hashedPassword, name, email);   // null 가능
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (id == null) {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 하는데에 실패했습니다.");
		} else {
			result.put("code", 200);
			result.put("result", "성공");
		}
		return result;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// 비밀번호 hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// db 조회 (loginId, 해싱된 비밀번호)
		UserEntity userEntity = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		
		if (userEntity != null) {
			//로그인 처리
			
			// 세션 저장 => 한번 로그인 유지 및 정보저장
			HttpSession session = request.getSession();
			session.setAttribute("userId", userEntity.getId());
			session.setAttribute("userName", userEntity.getName());
			session.setAttribute("userLoginId", userEntity.getLoginId());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			//로그인 불가
			result.put("code", 500);
			result.put("errorMessage", "존재하지않는 사용자입니다.");
		}
		
		return result;
	}
	
	
	
}
