package com.example.Memo.post.BO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Memo.common.FileManagerServices;
import com.example.Memo.post.DAO.PostDAO;
import com.example.Memo.post.Model.Post;

@Service
public class PostBO {
	
	//private Logger log = LoggerFactory.getLogger(PostBO.class);
	private Logger log = LoggerFactory.getLogger(this.getClass()); //모든곳에서 이 줄만 복사하면 해당 클래스 적용됨.
	
	
	@Autowired
	private PostDAO postDAO;
	@Autowired
	private FileManagerServices fileMangagerServies;
	
	// db저장할때 session에서 들고온 id 보내줘야함.
	public int addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		if ( file != null) {
			//파일이 있을 때만 업로드 처리. => 서버에 이미지를 업로드 처리. (컴터에 저장)
			imagePath = fileMangagerServies.saveFile(userLoginId, file);
			
		}
		//  db insert => dao 에 요청
		return postDAO.insertPost(userId, subject, content, imagePath);
	}
	
	//update 수정
	public int updatePost(int postId, int userId , String userLoginId ,String subject, String content, MultipartFile file) {
		
		//업데이트 할때 
		//기존글을 가지고 온다. (post 존재유무 확인, 이미지 교체가 될때 기존 이미지를 제거해야함 서버에서)
		Post post = getPostByPostId(postId);
		if (post == null) {
			log.warn("[update post] 수정할 메모가 존재하지 않습니다. postId :{}, userId :{}" ,postId, userId);
			return 0;
		}
		
		//파일이 있으면 이미지 수정 => 업로드 할때 만약실패되도 기존 이미지는 남겨둬야함. => 만약 성공하면 기존 이미지 서버에서 제거.
		String imagePath = null;
		if( file!= null) {
			// 업로드
			imagePath = fileMangagerServies.saveFile(userLoginId, file);
			//업로드가 성공하면 => 기존 이미지 제거
			if (imagePath != null && post.getImagePath() != null) {
				//업로드가 실패 할 수 있으므로 업로드가 된 후 이미지 제거
				fileMangagerServies.deleteFile(post.getImagePath());
			}
		}
		
		
		
		//db update => imagePath 가 없으면 마이바티스에서 update 치지 않도록 처리.
		return postDAO.updatePost(postId, userId, subject, content, imagePath);
	}
	
	
	// db select 
	public List<Post> getPost(int userId){
		
		return postDAO.selectPost(userId);
		
	}
	
	
	//게시글 상세페이지
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postDAO.selectPostByPostIdAndUserId(postId, userId);
	}

	
	public Post getPostByPostId(int postId) {
		return postDAO.selectPostByPostId(postId);
	}
	
	
}