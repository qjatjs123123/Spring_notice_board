package com.pjt.springbbs.reply.dao;

import java.util.List;
import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.reply.reply;

public interface IreplyDAO {
	int getLast(String bbsID);
	int getNext();
	List<reply> getList(String bbsID, String index);
	int write(int bbsID,String userID,String replyContent);
	String getDate();
	int delete(String replyID);
}
