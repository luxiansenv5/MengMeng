package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private Integer userId;
	private String userName;
	private String userPhoto;
	private String address;
	private Boolean userSex;
	private String overWrite;
	
	public User(){
		
	}

	public User(Integer userId, String userName, String userPhoto, String address,
				Boolean userSex, String overWrite) {
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
		this.address = address;
		this.userSex = userSex;
		this.overWrite = overWrite;
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

	public Boolean getUserSex() {
		return userSex;
	}

	public void setUserSex(Boolean userSex) {
		this.userSex = userSex;
	}

	public String getOverWrite() {
		return overWrite;
	}

	public void setOverWrite(String overWrite) {
		this.overWrite = overWrite;
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
		dest.writeValue(this.userSex);
		dest.writeString(this.overWrite);
	}

	protected User(Parcel in) {
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userName = in.readString();
		this.userPhoto = in.readString();
		this.address = in.readString();
		this.userSex = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.overWrite = in.readString();
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
