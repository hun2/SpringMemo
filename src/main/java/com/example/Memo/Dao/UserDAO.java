package com.example.Memo.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {

	
	public boolean existLoginId(String loginid);
	
	
	
	public void insertUserId(
			@Param("loginId") String loginId,
			@Param("password")  String password,
			@Param("name") String name,
			@Param("email") String email);
}
