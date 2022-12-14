package com.example.Memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Memo.post.BO.PostBO;

@RestController
public class PostRestController {

	
	@Autowired
	private PostBO postBO;
	
	
	
	/**
	 * 글쓰기 
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/post/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required = false) String content,
			@RequestParam(value="file", required = false) MultipartFile file, HttpSession session){
		
		//세션은 애초에 set으로 로그인할때 선언해두었다면 어디페이지를 가든 get으로 가져올수있음. 내가 생각하는 연결고리 방식이 절대아님!!!!!
		//추가로 Bo쪽으로 보낼때 db table 에 보면 userId 가 필요한데, 세션을 통해서 userid를 가져온 후에 
		//bo쪽으로 userid값을 보내야함. 파라미터로.
		String userLoginId =  (String) session.getAttribute("userLoginId");
		Integer userId = (Integer) session.getAttribute("userId");
		
		
		//db insert => 성공여부를 int로 하겠음. 
		int row  = postBO.addPost(userId, userLoginId, subject, content, file);
		
		
		Map<String,Object> result = new HashMap<>();
		if ( row > 0) {
			result.put("code", 100);
			result.put("result", "success");
		} else {
			result.put("errorMessage", "메모 저장에 실패했습니다.");
		}
		return result;		
	}
	
	/**
	 * 글수정
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/post/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required = false) String content,
			@RequestParam(value="file", required = false) MultipartFile file,
			HttpSession session
			){
		Map<String, Object> result = new HashMap<>();
		
		String userLoginId =  (String) session.getAttribute("userLoginId");
		int userId = (int) session.getAttribute("userId");
		
		int count =  postBO.updatePost(postId, userId, userLoginId, subject, content, file);
		if (count >0) {
			result.put("code", 100);
		} else {
			result.put("code", 400);
			result.put("errorMessage", "글 수정에 실패하였습니다.");
		}
		
		return result;
		
	}
	
	
	
	
	@DeleteMapping("/post/delete")
	public Map<String, Object> delete (@RequestParam("postId") int id) {
		
		//db  삭제
		int row = postBO.deletePostById(id);
		
		//응답
		Map<String, Object> result  = new HashMap<>(); 
		if ( row > 0) {
			result.put("code", 100);
		} else  {
			result.put("errorMessage", "삭제에 오류가 있습니다");
		}
		
		return result;
	}
	
	
}
