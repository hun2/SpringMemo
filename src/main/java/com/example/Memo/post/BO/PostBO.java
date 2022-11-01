package com.example.Memo.post.BO;

import java.util.Collections;
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
	
	//페이징 limit 갯수 선언
	private static final int POST_MAX_SIZE = 3;
	
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
	public List<Post> getPost(int userId, Integer prevId, Integer nextId){
		
		
		//페이징
		//게시글 번호가 : 10 9 8 /       7 6 5  /          4 3 2  /       1 구성이라 예
		//만약 432 페이지에 있을때,
		// 1) 이전을 눌렀을 때 => 정방향으로 4보다 큰거 3개를 가지고 온 후 (5,6,7) =>   쿼리에서 asc (7,6,5) 를 다시 하면 된다.
		// 2) 다음을 눌렀을때 => 5보다 작은거 들고오면 끝.
		
		//기준페이지
		Integer standardId = null;
		
		//방향
		String direction = null;
		
		if (prevId != null) {
			//이전 클릭
			standardId = prevId;
			direction = "prev";
			
			List<Post> postList =  postDAO.selectPost(userId, standardId, direction, POST_MAX_SIZE);
			Collections.reverse(postList);
			
			return postList;
			
		} else if ( nextId != null)  {
			//다음클릭
			standardId = nextId;      // 기준 아이디
			direction = "next";       // 방향
		}
		
		
		//첫페이지 일때는 standardId 가 null, 다음일떄는 값이 있음.
		return postDAO.selectPost(userId, standardId, direction, POST_MAX_SIZE);
	}
	
	
	//next 방향의 끝인가
	public boolean isLastPage(int nextId, int userId) {
		//nextId와 제일 작은 id가 같은가?
		
		int postId =  postDAO.selectPostIdByUserIdandSort(userId, "ASC");
		
		return postId == nextId; // 같으면 마지막 페이지
	}
	
	public boolean isFirstPage(int prevId, int userId) {
		
		int postId = postDAO.selectPostIdByUserIdandSort(userId, "DESC");
		
		return postId == prevId;
	}
	
	
	
	//게시글 상세페이지
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postDAO.selectPostByPostIdAndUserId(postId, userId);
	}

	
	public Post getPostByPostId(int postId) {
		return postDAO.selectPostByPostId(postId);
	}
	
	
	
	
	
	//삭제
	public int deletePostById(int id) {
		
		Post post = getPostByPostId(id);
		if (post == null) {
			log.warn("[delete post] 삭제할 게시글이 없습니다. postId :{}", id);
			return 0;
		}
		
		if (post.getImagePath() != null ) {
			fileMangagerServies.deleteFile(post.getImagePath());
		}
		return postDAO.deletePostById(id);
		
	}
	
}