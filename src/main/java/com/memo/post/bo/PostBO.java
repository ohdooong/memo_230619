package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	
	// input : userId			output:List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input : postId, userId   output:Post
	public Post getPostByPostIdUserId(int userId, int postId) {
		return postMapper.selectPostByPostIdUserId(userId, postId);
	}
	
	
	public void addPost(int userId, String userLoginId,  String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		postMapper.insertPost( subject, content,imagePath ,  userId);
	}
	
	
	
	
}
