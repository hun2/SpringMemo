package com.example.Memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Memo.Bo.UserBO;
import com.example.Memo.common.EncryptUtils;
import com.example.Memo.model.User;

@RestController      //=> controller + responsebody 가 되어있어 항상 json 내림.
public class UserRestController {
	
	@Autowired
	private UserBO userBo;
	
	
	/**
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@GetMapping("/user/is_duplicated_id")
	public Map<String, Object> isduplicatedId(@RequestParam("loginId") String loginId){
		Map<String, Object> result = new HashMap<>();
		boolean isDuplicated = userBo.existLoginId(loginId);
		if(isDuplicated) {
			result.put("result", true); //중복
			result.put("code", 100); //성공
			
		} else {
			result.put("result", false);// 중복아님
			result.put("code", 100); //성공
		}
		return result;
	}
	
	
	/**
	 * 회원가입 API
	 * 비밀번호 md5 적용하여 보내기.
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/user/sign_up_for_submit")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password, 
			@RequestParam("name") String name, 
			@RequestParam("email") String email){
		
		//PASSWORD 암호화 => 해싱              AAAA => sa12#!@>#!dasd 로 해싱이 된다 생각하면됨. 단방향
		//md5 => 개인프로젝트일때는 더 좋은걸로 적용하셈.
		String encryptPassword =  EncryptUtils.md5(password);
		userBo.addUserId(loginId, encryptPassword, name, email);
		
		//정상적으로 db insert가 되면 밑으로 내려감.
		Map<String , Object> result = new HashMap<>();
		result.put("code", 100);
		result.put("result", "success");
		return result;
	}
	
	
	@PostMapping("/user/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password, HttpServletRequest request
			){
		// password 해싱 먼저 하고 =>  db로 보낸후에 id랑 비번이 맞는지 확인.
		// 응답값이 있으면 로그인시켜주고 응답값이 없으면 잘못된 정보.
		String encryptPassword =  EncryptUtils.md5(password);
		Map<String, Object> result = new HashMap<>();
		User user = userBo.getLoginIdPassword(loginId, encryptPassword);
		
		if (user != null) {
			result.put("code", 100);
			result.put("result", "success");
			
			//세션에 유저 정보를 담는다.
			HttpSession session =  request.getSession(); // 세션에 저장.
			session.setAttribute("userName", user.getName());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userId", user.getId());
		} else {
			result.put("code", 400);
			result.put("errorMessage", "존재하지 않는 사용자 입니다.");
		}
		return result ;
	}
	
}
