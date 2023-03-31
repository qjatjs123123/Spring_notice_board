package com.pjt.springbbs.user.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pjt.springbbs.encript.BCrypt;
import com.pjt.springbbs.user.user;
import com.pjt.springbbs.util.SHA256;

@Component
public class UserDAO implements IUserDAO{

	private DriverManagerDataSource dataSource;
	private JdbcTemplate template;
	
	@Autowired
	public UserDAO(ComboPooledDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	
	@Override
	public int join(user user) {
		int result = 0;
		
		String SQL = "Insert into user values(?,?,?,?,?,?)";	
		String userNum = user.getFirstnum()+user.getSecondnum();		
		result = template.update(SQL, user.getId(), BCrypt.hashpw(userNum, BCrypt.gensalt(9)),
				BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt(10))
				,user.getName(),user.getEmail(),SHA256.getSHA256(user.getEmail()));
		return result;
	}
	
	@Override
	public boolean id_check(final String id) {
		List<user> user = null;
		String SQL = "SELECT * FROM user WHERE userID = ?";

		user = template.query(SQL, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, id);			
			}
			
		},  new RowMapper<user>() {

			@Override
			public com.pjt.springbbs.user.user mapRow(ResultSet rs, int rowNum) throws SQLException {
				user mem = new user();
				mem.setId(rs.getString(1));
				return mem;
			}
			
		});
		
		if(user.isEmpty()) return true;
		else return false;
	}
	
	@Override
	public String findId(final String name,final String firstnum,final String secondnum) {
		List<user> user = null;
		String result = null;
		String SQL = "SELECT userID, userNum from user where userName = ?";
		final String userNum = firstnum+secondnum;
		
		
		user = template.query(SQL, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, name);			
			}
			
		},  new RowMapper<user>() {

			@Override
			public user mapRow(ResultSet rs, int rowNum) throws SQLException {
				//rs 인덱스 첫 번째
				if(BCrypt.checkpw(userNum, rs.getString(2))) {
					user mem = new user();
					mem.setId(rs.getString(1));				
					return mem;
				}
				//rs 인덱스 첫 번째 이후
				while(rs.next()) {
					if(BCrypt.checkpw(userNum, rs.getString(2))) {
						user mem = new user();
						mem.setId(rs.getString(1));						
						return mem;
					}
				}
				return null;
			}
			
		});		
		if(user.isEmpty() || user.get(0) == null) return null;
		else return user.get(0).getId();		
	}


	@Override
	public String getUserEmail(final String id, final String firstnum,final String secondnum,final String email) {
		String SQL = "SELECT userEmail,userNum from user where userID = ? and userEmail = ?";
		
		final String userNum = firstnum+secondnum;
		
		List<user> user = null;
		
		user = template.query(SQL, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, id);		
				pstmt.setString(2, email);	
			}
			
		},  new RowMapper<user>() {

			@Override
			public user mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				//rs 인덱스 첫 번째
				if(BCrypt.checkpw(userNum, rs.getString(2))) {
					
					user mem = new user();
					mem.setEmail(rs.getString(1));			
					return mem;
				}

				return null;
			}
			
		});	
		
		if(user.isEmpty()) return null;
		else return user.get(0).getEmail();	
	
	}

	@Override
	public int UpdatePassWord(String id, String passwd) {
		String SQL = "UPDATE user SET userPassword = ? WHERE userID= ?";
		int result = -1;
		result = template.update(SQL,BCrypt.hashpw(passwd, BCrypt.gensalt(10)),id);
		return result;
	}


	@Override
	public int login(final String id, final String passwd) {
		String SQL = "SELECT userPassword FROM user where userID = ?";
		List<user> user = null;
		
		user = template.query(SQL, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, id);			
			}
			
		},  new RowMapper<user>() {

			@Override
			public user mapRow(ResultSet rs, int rowNum) throws SQLException {
				//rs 인덱스 첫 번째				
				if(BCrypt.checkpw(passwd, rs.getString(1))) {
					user mem = new user();
					mem.setPasswd(rs.getString(1));			
					return mem;
				}
				return null;
			}
			
		});	
		
		
		if(user.isEmpty())
			return 0;
		
		else
		{
			if(user.get(0) == null) return 0;
			else return 1;
		}
	}


	@Override
	public String getUserEmail(String id) {
		String SQL = "SELECT userEmail from user where userID = '" + id + "'";
		String result = template.queryForObject(SQL, String.class);
		return result;
		
	}
	


}
