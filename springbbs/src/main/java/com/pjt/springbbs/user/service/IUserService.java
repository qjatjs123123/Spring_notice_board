package com.pjt.springbbs.user.service;

import com.pjt.springbbs.user.user;

public interface IUserService {
	int userRegister(user user);
	boolean id_check(String id);
	String id_find(String name,String firstnum,String secondnum);
	String emailSend(String id, String firstnum, String secondnum, String email);
	int ChangePassword(String id, String passwd);
	int userLogin(String id, String passwd);
	String getUserEmail(String id);
}
