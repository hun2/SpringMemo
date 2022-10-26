package com.example.Memo.post.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.Memo.post.Model.Post;

@Repository
public interface PostDAO {

	
	public int insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	
	
	public List<Post> selectPost();
}
