package com.example.mengmeng.pojo;

public class UserInfo {
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	private String address;
	private boolean userSex;
	private String userWrite;
	private String token;
	
	public UserInfo(){}
	
	public UserInfo( String userName, String userPhoto,
			String address){
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
	}
	
	public UserInfo(Integer userId, String userName, String userPhoto,
			String address, boolean userSex, String userWrite, String token) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
		this.userSex = userSex;
		this.userWrite = userWrite;
		this.token = token;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isSex() {
		return userSex;
	}
	public void setSex(boolean sex) {
		this.userSex = userSex;
	}
	public String getUserWrite() {
		return userWrite;
	}
	public void setUserWrite(String userWrite) {
		this.userWrite = userWrite;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
