package com.pjt.springbbs.reply.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.reply.reply;

@Component
public class replyDAO implements IreplyDAO{

	private DriverManagerDataSource dataSource;
	private JdbcTemplate template;
	
	@Autowired
	public replyDAO(ComboPooledDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public int getLast(String bbsID) {
		String SQL = "Select * from reply where replyID <= " + getNext() + " AND replyAvailable = 1 AND bbsID = " + bbsID + " ORDER BY replyID DESC LIMIT 10";
		
		List<reply> results = template.query(
				SQL,
				new RowMapper<reply>() {
					@Override
					public reply mapRow(ResultSet rs, int rowNum) throws SQLException {
						reply r = new reply();
						r.setReplyID(rs.getInt("replyID"));
						return r;
					}
				});
			
			return results.isEmpty() ? 0 : results.get(results.size()-1).getReplyID();
	}

	@Override
	public int getNext() {
		String SQL = "SELECT replyID FROM reply ORDER BY replyID DESC"; 
		
		List<reply> results = template.query(
				SQL,
				new RowMapper<reply>() {
					@Override
					public reply mapRow(ResultSet rs, int rowNum) throws SQLException {
						reply r = new reply();
						r.setReplyID(rs.getInt("replyID"));
						return r;
					}
				});
			return results.isEmpty() ? 0 : results.get(0).getReplyID();
	}

	@Override
	public List<reply> getList(String bbsID, String index) {
		String SQL = "Select * from reply where replyID < ? AND replyAvailable = 1 AND bbsID = ? ORDER BY replyID DESC LIMIT 10";
		int first = 0;
		int second = 0;
		if(Integer.parseInt(index) == -1) {
			first = getNext()+1; // getNext() => 다음 저장될 ID번호, getNext() - (pageNumber-1)*10 => 1~10으로 정규화
			second = Integer.parseInt(bbsID);
		}
		else {
			first = Integer.parseInt(index);
			second = Integer.parseInt(bbsID);
		}
		List<reply> actors = this.template.query(
				  SQL,
				  new Object[]{first,second},
				  new RowMapper<reply>() {
				    public reply mapRow(ResultSet rs, int rowNum) throws SQLException {
				    	reply b = new reply();
				    	b.setBbsID(rs.getInt(1));
						b.setReplyID(rs.getInt(2));
						b.setUserID(rs.getString(3));
						b.setReplyContent(rs.getString(4));
						b.setReplyDate(rs.getString(5));
						b.setReplyAvailable(rs.getInt(6));
				      return b;
				    }
				  });
		return actors;
	}

	@Override
	public int write(int bbsID, String userID, String replyContent) {
		String SQL = "Insert into reply values (?,?,?,?,?,?)";		
		int result = template.update(SQL,bbsID,getNext()+1, userID,replyContent,getDate(),1);
		return result;
	}

	@Override
	public String getDate() {
		String SQL = "SELECT NOW()"; // 현재시간을 가져오는 MYSQL 문장
		String result = template.queryForObject(SQL, String.class);
		return result;
	}

	@Override
	public int delete(String replyID) {
		String SQL = "update reply set replyAvailable = 0 where replyID = ?";
		int result = template.update(SQL,replyID);
		return result;

	}
}
