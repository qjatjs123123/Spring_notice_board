package com.pjt.springbbs.bbs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.bbs.dao.bbsDAO;

@Service
public class bbsService implements IbbsService{

	@Autowired
	bbsDAO dao;
	
	@Override
	public List<bbs> getList(int pageNumber) {
		
		List<bbs> result = dao.getList(pageNumber);
		return result;
	}

	@Override
	public int getMaxPage() {
		int result = dao.MaxPageCount();
		return result;
	}

	@Override
	public int bbsWrite(String bbsTitle, String userID, String bbsContent) {
		int result = dao.write(bbsTitle, userID, bbsContent);
		return result;
	}

	@Override
	public bbs getBbsService(String bbsID) {
		bbs result = dao.getBbs(bbsID);
		return result;
	}

	@Override
	public int updateService(String bbsID, String bbsTitle, String bbsContent) {
		int result = dao.update(bbsID, bbsTitle, bbsContent);
		return result;
	}

	@Override
	public int deleteService(String bbsID) {
		int result = dao.delete(bbsID);
		return result;
	}

}
