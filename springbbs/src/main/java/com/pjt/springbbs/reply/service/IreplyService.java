package com.pjt.springbbs.reply.service;

import java.util.List;

import com.pjt.springbbs.reply.reply;

public interface IreplyService {
	int getLastService(String bbsID);
	int getNextService();
	List<reply> getListService(String bbsID, String index);
	int writeService(int bbsID,String userID,String replyContent);
	int deleteService(String replyID);
}
