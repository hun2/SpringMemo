package com.example.Memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Memo.Bo.UserBO;
import com.example.Memo.common.EncryptUtils;

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
	
	
	
	@PostMapping("/user/sign_up_for_submit")
	public Map<String, Object> singUp(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password, 
			@RequestParam("name") String name, 
			@RequestParam("email") String email){
		
		//PASSWORD 암호화 => 해싱              AAAA => sa12#!@>#!dasd 로 해싱이 된다 생각하면됨. 단방향
		//md5 => 개인프로젝트일때는 더 좋은걸로 적용하셈.
		String encryptPassword =  EncryptUtils.md5(password);
		userBo.addUserId(loginId, encryptPassword, name, email);
		
		//db insert
		Map<String , Object> result = new HashMap<>();
		result.put("code", 100);
		result.put("result", "success");
		
		
		
		
		return result;
	}
	
	
}
