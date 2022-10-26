package com.example.Memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Memo.post.BO.PostBO;
import com.example.Memo.post.Model.Post;

@Controller
public class PostController {

	@Autowired
	private PostBO postBo;
	
	//로그인 후 글 리스트
	@RequestMapping("/post/post_list_view")
	public String postListView(HttpSession session, Model model) {
		
		//로그인이 풀려있으면 로그인 페이지로 리다이렉트
		Integer userId =  (Integer) session.getAttribute("userId"); // 로그인이 풀려있으면 null이기때문에 Integer (int 는 안됨)
		if ( userId == null) {
			return "redirect://user/sign_in_view";
		}
		model.addAttribute("viewName", "post/postList");
		
		
		//db select
		List<Post> post =  postBo.getPost(userId);
		model.addAttribute("posts", post);
		
		
		return "template/layout";
	}
	
	
	//글쓰기
	@RequestMapping("/post/post_create_view")
	public String postCreatView(HttpSession session, Model model) {
		Integer userId =  (Integer) session.getAttribute("userId"); //  로그인이 풀려있으면 null이기때문에 Integer (int 는 안됨)
		if (userId == null) {
			return "redirect:/user/sing_in_view";
		}
		model.addAttribute("viewName", "post/postCreate");
		return "template/layout";
	}
	
	
	//
	@GetMapping("/post/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId, HttpSession session, Model model) {
		//로그인이 됬는지 체크
		Integer userId =  (Integer) session.getAttribute("userId");
		
		if (userId == null) {
			
			return "redirect://user/sign_in_view";
		}
		
		// postId 에 해당되는 데이터를 가져와서 model에 담는다.
		Post post = postBo.getPostByPostIdAndUserId(postId, userId);
		model.addAttribute("post", post);
		
		
		//layout 화면 이동
		model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}
	
	
	
}
