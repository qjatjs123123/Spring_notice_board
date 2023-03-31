package com.pjt.springbbs.user;

public class user {
	private String id;
	private String firstnum;
	private String secondnum;
	private String passwd;
	private String name;
	private String email;
	
	
	public String getFirstnum() {
		return firstnum;
	}
	public void setFirstnum(String firstnum) {
		this.firstnum = firstnum;
	}
	public String getSecondnum() {
		return secondnum;
	}
	public void setSecondnum(String secondnum) {
		this.secondnum = secondnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
