package com.pjt.springbbs.bbs.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pjt.springbbs.bbs.bbs;
import com.pjt.springbbs.encript.BCrypt;
import com.pjt.springbbs.user.user;



@Component
public class bbsDAO implements IbbsDAO{

	private DriverManagerDataSource dataSource;
	private JdbcTemplate template;
	private static HashMap<Integer, Integer> head_list;
	private static HashMap<Integer, Integer> tail_list;
	private static int k = -1;
	
	@Autowired
	public bbsDAO(ComboPooledDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
		if(k == -1) {
			head_list = new HashMap<Integer, Integer>(); 
			tail_list = new HashMap<Integer, Integer>(); 
			k=0;
		}	
	}
	
	@Override
	public List<bbs> getList(final int pageNumber) {
		
		String SQL = "Select * from bbs where bbsID <= " +getNext()+ " AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		
		if(pageNumber == 1) {
		return this.template.query(SQL, new Object[] {},
		        new RowMapper<bbs>() {
		            public bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	if (rowNum == 0)
						{								  
							head_list.put(pageNumber,rs.getInt(1));
						}
		                bbs b = new bbs();
		                b.setBbsID(rs.getInt(1));
						b.setBbsTitle(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
						b.setUserID(rs.getString(3));
						b.setBbsDate(rs.getString(4));
						b.setBbsContent(rs.getString(5));
						b.setBbsAvailable(rs.getInt(6));
						tail_list.put(pageNumber,rs.getInt(1));
		                return b;
		                
		            }
		        }
		    );
		}else {
			if(head_list.size()<pageNumber) {
				SQL = "Select * from bbs where bbsID < "+ (Integer)tail_list.get(pageNumber-1)+ " AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				return this.template.query(SQL, new Object[] {},
				        new RowMapper<bbs>() {
				            public bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
				            	if (rowNum == 0)
								{							
										head_list.put(pageNumber,rs.getInt(1));
								}
				                bbs b = new bbs();
				                b.setBbsID(rs.getInt(1));
								b.setBbsTitle(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
								b.setUserID(rs.getString(3));
								b.setBbsDate(rs.getString(4));
								b.setBbsContent(rs.getString(5));
								b.setBbsAvailable(rs.getInt(6));
								tail_list.put(pageNumber,rs.getInt(1));
				                return b;
				                
				            }
				        }
				    );
			}
			else {
				SQL = "Select * from bbs where bbsID <= " + (Integer)head_list.get(pageNumber) 
					+ " AND bbsID >= " + (Integer)tail_list.get(pageNumber) 
					+ " AND bbsAvailable = 1 ORDER BY bbsID DESC ";
				
				return this.template.query(SQL, new Object[] {},
				        new RowMapper<bbs>() {
				            public bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
				                bbs b = new bbs();
				                b.setBbsID(rs.getInt(1));
								b.setBbsTitle(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
								b.setUserID(rs.getString(3));
								b.setBbsDate(rs.getString(4));
								b.setBbsContent(rs.getString(5));
								b.setBbsAvailable(rs.getInt(6));
				                return b;
				                
				            }
				        }
				    );
			}
		}
		
		
	}

	@Override
	public int getNext() {
		String SQL = "SELECT bbsID FROM bbs ORDER BY bbsID DESC"; 
		
		List<bbs> results = template.query(
				"SELECT bbsID FROM bbs ORDER BY bbsID DESC",
				new RowMapper<bbs>() {
					@Override
					public bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
						bbs b = new bbs();
						b.setBbsID(rs.getInt("bbsID"));

						return b;
					}
				});
			return results.isEmpty() ? 0 : results.get(0).getBbsID();
		
	}

	@Override
	public int MaxPageCount() {
		String SQL = "SELECT COUNT(*) FROM bbs where bbsAvailable = 1";
		
		int count = template.queryForInt(SQL);
		int page = count/10;
		int res = count%10;
		if (res == 0)
			return page;
		else
			return page+1;
		
	}

	@Override
	public int write(String bbsTitle, String userID, String bbsContent) {
		head_list.clear();
		tail_list.clear();
		String SQL = "Insert into bbs values (?,?,?,?,?,?)"; 
		int result = template.update(SQL,getNext()+1,bbsTitle, userID,getDate(),bbsContent,1);
		return result;
	}

	@Override
	public String getDate() {
		String SQL = "SELECT NOW()"; // 현재시간을 가져오는 MYSQL 문장
		String result = template.queryForObject(SQL, String.class);
		return result;
	}

	@Override
	public bbs getBbs(String bbsID) {
		
		String SQL = "Select * from bbs where bbsID = " + bbsID;
		bbs actor = this.template.queryForObject(
				SQL,
				  new Object[] {},
				  new RowMapper<bbs>() {
				    public bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
				      bbs b = new bbs();
				      b.setBbsID(rs.getInt(1));
				      b.setBbsTitle(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
				      b.setUserID(rs.getString(3));
				      b.setBbsDate(rs.getString(4));
				      b.setBbsContent(rs.getString(5).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
				      b.setBbsAvailable(rs.getInt(6));
				      return b;
				    }
				  });
		return actor;
	}

	@Override
	public int update(String bbsID, String bbsTitle, String bbsContent) {
		String SQL = "update bbs set bbsTitle = ?, bbsContent = ? where bbsID = ?"; 
		int result = template.update(SQL,bbsTitle, bbsContent,bbsID);
		return result;
		
	}

	@Override
	public int delete(String bbsID) {
		head_list.clear();
		tail_list.clear();
		String SQL = "update bbs set bbsAvailable = 0 where bbsID = ?"; 
		int result = template.update(SQL,bbsID);
		return result;

	}

}
