package com.example.Memo.Bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Memo.Dao.UserDAO;
import com.example.Memo.model.User;

@Service
public class UserBO {

	@Autowired
	private UserDAO userDao;
	
	//아이디 중복확인
	public boolean existLoginId(String loginid) {
		return userDao.existLoginId(loginid);
	}
	
	//회원가입
	public void addUserId(String loginId, String password, String name, String email) {
		userDao.insertUserId(loginId, password, name, email);
	}
	
	
	//로그인 id 및 password 확인.
	public User getLoginIdPassword(String loginId, String password) {
		
		return userDao.selectLoginIdPassword(loginId, password);
	}
}
