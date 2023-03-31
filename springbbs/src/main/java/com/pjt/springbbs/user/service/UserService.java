package com.pjt.springbbs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.springbbs.user.user;
import com.pjt.springbbs.user.dao.UserDAO;

@Service
public class UserService implements IUserService{

	@Autowired
	UserDAO dao;
	
	@Override
	public int userRegister(user user) {
		int result = dao.join(user);
		return result;
	}

	@Override
	public boolean id_check(String id) {
		boolean result = dao.id_check(id);
		return result;
	}

	@Override
	public String id_find(String name, String firstnum, String secondnum) {
		String result = dao.findId(name, firstnum, secondnum);
		return result;
	}

	@Override
	public String emailSend(String id, String firstnum, String secondnum, String email) {
		String result = dao.getUserEmail(id, firstnum, secondnum, email);		
		return result;
	}

	@Override
	public int ChangePassword(String id, String passwd) {
		int result = dao.UpdatePassWord(id, passwd);
		return result;
	}

	@Override
	public int userLogin(String id, String passwd) {
		int result = dao.login(id, passwd);
		return result;
	}

	@Override
	public String getUserEmail(String id) {
		String result = dao.getUserEmail(id);
		return result;
	}
}
