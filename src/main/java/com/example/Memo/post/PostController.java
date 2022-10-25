package com.example.Memo.post;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {

	
	//로그인 후 글 리스트
	@RequestMapping("/post/post_list_view")
	public String postListView(HttpSession session, Model model) {
		Integer userId =  (Integer) session.getAttribute("userId"); // 로그인이 풀려있으면 null이기때문에 Integer (int 는 안됨)
		if ( userId == null) {
			//로그인이 풀려있으면 로그인 페이지로 리다이렉트
			return "redirect://user/sign_in_view";
			
		}
		model.addAttribute("viewName", "post/postList");
		
		
		//db select
		
		
		
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
	
	
	
	
}
