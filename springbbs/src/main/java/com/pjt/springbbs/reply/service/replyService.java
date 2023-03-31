package com.pjt.springbbs.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.reply.reply;
import com.pjt.springbbs.reply.dao.replyDAO;

@Service
public class replyService implements IreplyService{

	@Autowired
	replyDAO dao;

	@Override
	public int getLastService(String bbsID) {
		int result = dao.getLast(bbsID);
		return result;
	}

	@Override
	public int getNextService() {
		int result = dao.getNext();
		return result;
	}

	@Override
	public List<reply> getListService(String bbsID, String index) {
		List<reply> result = dao.getList(bbsID, index);
		return result;
	}

	@Override
	public int writeService(int bbsID, String userID, String replyContent) {
		int result = dao.write(bbsID, userID, replyContent);
		return result;
	}

	@Override
	public int deleteService(String replyID) {
		int result = dao.delete(replyID);
		return result;
	}


	
}
