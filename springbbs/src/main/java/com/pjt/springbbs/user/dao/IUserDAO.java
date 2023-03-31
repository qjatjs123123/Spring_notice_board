package com.pjt.springbbs.user.dao;

import com.pjt.springbbs.user.user;


public interface IUserDAO {
	int join(user user);
	boolean id_check(String id);
	String findId(String name, String firstnum, String secondnum);
	String getUserEmail(String id, String firstnum, String secondnum, String email);
	int UpdatePassWord(String id, String passwd);
	int login(String id, String passwd);
	String getUserEmail(String id);
}
