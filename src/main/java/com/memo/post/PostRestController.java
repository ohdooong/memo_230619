package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			HttpSession session) {
		String imagePath = null;
		
		//mybatis
		
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 안한 사용자는 못한다는 가정
		int userId = (int)session.getAttribute("userId");
		
		postBO.addPost(subject, content, userId, imagePath);
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
		
		
		
	}
}
