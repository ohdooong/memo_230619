package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		
		//mybatis
		
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 안한 사용자는 못한다는 가정
		int userId = (int)session.getAttribute("userId");
		
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		postBO.addPost(userId, userLoginId, subject, content, file);
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
		
	}
}
