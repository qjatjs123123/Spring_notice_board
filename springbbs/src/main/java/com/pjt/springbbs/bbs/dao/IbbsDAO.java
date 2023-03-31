package com.pjt.springbbs.bbs.dao;

import java.util.ArrayList;
import java.util.List;

import com.pjt.springbbs.bbs.bbs;

public interface IbbsDAO {
	List<bbs> getList(int pageNumber);
	int getNext();
	int MaxPageCount();
	int write(String bbsTitle, String userID, String bbsContent);
	String getDate();
	bbs getBbs(String bbsID);
	int update(String bbsID, String bbsTitle, String bbsContent);
	int delete(String bbsID);
}
