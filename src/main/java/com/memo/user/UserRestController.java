package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@RequestMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicatedId (
			@RequestParam("loginId") String loginId) {
		
		Map<String, Object> result = new HashMap<>();
		
		// db조회
		
		
		
		// 응답값 만들고 리턴
		
		result.put("code", 200);
		result.put("isDuplicated", true);
		
		return result;
		
	}
	
	
	
}
