package com.example.Memo.post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Memo.common.FileManagerServices;
import com.example.Memo.post.DAO.PostDAO;
import com.example.Memo.post.Model.Post;

@Service
public class PostBO {
	
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
	
	// db select 
	public List<Post> getPost(){
		
		return postDAO.selectPost();
	}
	
}