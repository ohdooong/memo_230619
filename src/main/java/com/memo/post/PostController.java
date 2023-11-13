package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

@RequestMapping("/post")
@Controller
public class PostController {  // 화면 return
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value="prevId", required = false) Integer prevIdParam,
			@RequestParam(value="nextId", required = false) Integer nextIdParam,
			HttpSession session , 
			Model model) {
		
		Integer userId = (Integer)session.getAttribute("userId");   // 로그인이 안된상태에서 들어올 수도있다.    null 가정    =>    세션 존재 x
		
		if (userId == null) {
			// 비로그인이면 로그인 화면으로 이동
			return "redirect:/user/sign-in-view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int nextId = 0;
		if (postList.isEmpty() == false) {
			// postList가 비어있을 때 오류를 방지하기 위함
			nextId = postList.get(postList.size() - 1).getId();  // Post 객체에서 Id꺼낸다.
		}
		
		
		model.addAttribute("nextId", nextId);
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/postList");
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면
	 */
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/postCreate");
		return "template/layout";
	}
	
	
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		int userId = (int)session.getAttribute("userId");
		
		// DB select
		// select where postId  and  userId    =>   다른 사용자 사용방지
		Post post = postBO.getPostByPostIdUserId(userId, postId);
		
		model.addAttribute("post",post);
		model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}
	
	
	
	
	
	
}
