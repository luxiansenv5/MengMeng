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

	public User(){}

	public User(Integer userId){
		this.userId = userId;
	}

	public User( String userName, String userPhoto,
					 String address){
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
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
	}

	protected User(Parcel in) {
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userName = in.readString();
		this.userPhoto = in.readString();
		this.address = in.readString();
		this.userSex = in.readByte() != 0;
		this.userWrite = in.readString();
		this.token = in.readString();
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
