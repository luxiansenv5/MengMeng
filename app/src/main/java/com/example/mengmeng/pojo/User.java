package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private Integer userId;
	private String userName;
	private String userPhoto;
	
	public User(){
		
	}
	
	public User(Integer userId, String userName, String userPhoto) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPhoto = userPhoto;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.userId);
		dest.writeString(this.userName);
		dest.writeString(this.userPhoto);
	}

	protected User(Parcel in) {
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userName = in.readString();
		this.userPhoto = in.readString();
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
