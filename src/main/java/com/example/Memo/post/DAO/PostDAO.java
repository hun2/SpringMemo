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
	public int updatePost(
			@Param("postId")int postId,
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	
	public List<Post> selectPost(
			@Param("userId") int userId,
			@Param("standardId") Integer standardId,
			@Param("direction") String direction,
			@Param("limit") int limit);
	
	public int selectPostIdByUserIdandSort(
			@Param("userId") int userId,
			@Param("sort") String sort
			);
	
			
	
	
	public Post selectPostByPostIdAndUserId(
			@Param("postId")int postId, 
			@Param("userId") int userId);
	
	
	public Post selectPostByPostId(int postId);
	
	
	
	public int deletePostById(int id);
}
