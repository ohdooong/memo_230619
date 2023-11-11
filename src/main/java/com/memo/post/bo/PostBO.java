package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	// this => PostBO
	// LoggerFactory 오류  :   import 살펴볼것
	// 로그
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
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
	
	public void updatePost(int userId, String userLoginId, int postId, String subject, String content, MultipartFile file) {
		
		// 이미지 처리   =>   기존의 이미지를 삭제하지 않으면 지워야한다.
		// 기존 글을 가져와본다. (이미지 교체 시 삭제를 위해, 업데이트 대상 확인)
		// 확실히 존재하는 객체여도 Null 가능성 있음
		// 로그 찍을 때 sysout 쓰면 안됨   =>  ThreadSafe X => 동시접속 환경에선 절대 사용하면 안됨  =>  속도 저하
		
		Post post = postMapper.selectPostByPostIdUserId(userId, postId);
		if (post == null) {
			// log.error
			log.info("==================================");
			log.info("[글 수정] : post is null.  post id:{}, userId:{}",postId,userId);
			log.info("==================================");
			return;
		}
		
		// 파일이 있다면
		// 1) 새로운 이미지 업로드 먼저 함.
		// 2) 새 이미지 업로드 성공 시 기존 이미지 제거(기존 이미지가 있을 때)
		String imagePath = null;
		if (file != null) {
			// 업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			
			// 업로드 성공 시 기존 이미지 제거(있으면)
			if (imagePath != null && post.getImagePath() != null) {
				// 업로드가 성공을 했고, 기존 이미지가 존재한다면 => 삭제
				// 이미지 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB 글 update
		postMapper.updatePostByPostIdUserId(postId, userId, subject, content, imagePath);
		
		
	}
	
	// input : 글쓴이번호, 글 번호
	// output : x
	public void deletePostByPostIdUserId (int postId, int userId) {
		// 기존글 먼저 가져온다 (이미지 존재 시 삭제하기 위해)
		Post post = postMapper.selectPostByPostIdUserId(userId, postId);
		
		if (post == null) {
			log.info("[글 삭제]post ::::::::: null, postId:{}, userId:{}",postId,userId);
			log.info("[글 삭제]post ::::::::: null, postId:{}, userId:{}",postId,userId);
			log.info("[글 삭제]post ::::::::: null, postId:{}, userId:{}",postId,userId);
			return;
		}
		
		// 기존 이미지 존재 => 삭제한다.
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		// DB 삭제
		postMapper.deletePostByPostIdUserId(postId, userId);
		
	}
	
	
	
}
