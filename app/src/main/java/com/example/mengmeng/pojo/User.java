package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	private String address;
	private boolean userSex;
	private String userWrite;
	private String token;
	private String userPsd;
	private Integer followCount;
	
	public User(){}

	public User(Integer userId,String userName){
		this.userId=userId;
		this.userName=userName;
	}

	public User(Integer userId){
		this.userId=userId;
	}

	public User( Integer userId,String userName, String userPhoto,
			String address){
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
	}

	public User(Integer userId, String userName, String userPhoto
			){
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
	}
	

	public User( Integer userId,String userPsd,String userName,boolean userSex
			){
		this.userId = userId;
		this.userName = userName;
		this.userPsd = userPsd;
		this.userSex = userSex;
	}
	
	public User( String userName, String userPhoto,
			String address){
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
	}
	public User(Integer userId, String userName, String userPhoto,
			String address, boolean userSex, String userWrite) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
		this.userSex = userSex;
		this.userWrite = userWrite;
	}
	
	public User(Integer userId, String userName, String userPhoto,
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
	
	
	public User(Integer userId, String userName, String userPhoto,
			String address, boolean userSex, String userWrite, String token,
			String userPsd) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
		this.userSex = userSex;
		this.userWrite = userWrite;
		this.token = token;
		this.userPsd = userPsd;
	}
	public String getUserPsd() {
		return userPsd;
	}

	public void setUserPsd(String userPsd) {
		this.userPsd = userPsd;
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

	public boolean isUserSex() {
		return userSex;
	}

	public void setUserSex(boolean userSex) {
		this.userSex = userSex;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.userId);
		dest.writeString(this.userName);
		dest.writeString(this.userPhoto);
		dest.writeString(this.address);
		dest.writeByte(this.userSex ? (byte) 1 : (byte) 0);
		dest.writeString(this.userWrite);
		dest.writeString(this.token);
		dest.writeString(this.userPsd);
		dest.writeValue(this.followCount);
	}

	protected User(Parcel in) {
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userName = in.readString();
		this.userPhoto = in.readString();
		this.address = in.readString();
		this.userSex = in.readByte() != 0;
		this.userWrite = in.readString();
		this.token = in.readString();
		this.userPsd = in.readString();
		this.followCount = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
