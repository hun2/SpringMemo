package com.example.Memo.Bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Memo.Dao.UserDAO;

@Service
public class UserBO {

	@Autowired
	private UserDAO userDao;
	
	public boolean existLoginId(String loginid) {
		
		
		return userDao.existLoginId(loginid);
	}
	
	
	public void addUserId(String loginId, String password, String name, String email) {
	
	}
	
}
