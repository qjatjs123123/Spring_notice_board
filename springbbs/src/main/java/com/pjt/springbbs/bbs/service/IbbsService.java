package com.pjt.springbbs.bbs.service;

import java.util.ArrayList;
import java.util.List;

import com.pjt.springbbs.bbs.bbs;

public interface IbbsService {
	List<bbs> getList(int pageNumber);
	int getMaxPage();
	int bbsWrite(String bbsTitle, String userID, String bbsContent);
	bbs getBbsService(String bbsID);
	int updateService(String bbsID, String bbsTitle, String bbsContent);
	int deleteService(String bbsID);
}
