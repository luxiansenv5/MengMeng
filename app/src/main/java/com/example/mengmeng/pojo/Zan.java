package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Zan implements Parcelable {
	
	public Integer zanId;
	public User user;
	public Integer dynamicId;
	public Integer UserId;
	public Timestamp releaseTime;
	
	
	public Zan(Integer zanId, Integer dynamicId, Integer userId,
			Timestamp releaseTime) {
		super();
		this.zanId = zanId;
		this.dynamicId = dynamicId;
		this.UserId = userId;
		this.releaseTime = releaseTime;
	}
	public Zan(Integer dynamicId, Integer userId,
			Timestamp releaseTime) {
		super();
		this.dynamicId = dynamicId;
		this.UserId = userId;
		this.releaseTime = releaseTime;
	}
	
	
	public Zan(Integer zanId, User user, Integer dynamicId, Integer userId,
			Timestamp releaseTime) {
		super();
		this.zanId = zanId;
		this.user = user;
		this.dynamicId = dynamicId;
		UserId = userId;
		this.releaseTime = releaseTime;
	}
	public Zan(User user, Integer dynamicId, Integer userId,
			   Timestamp releaseTime) {
		super();
		this.user = user;
		this.dynamicId = dynamicId;
		UserId = userId;
		this.releaseTime = releaseTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Integer getZanId() {
		return zanId;
	}
	public void setZanId(Integer zanId) {
		this.zanId = zanId;
	}
	public Integer getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		this.UserId = userId;
	}
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.zanId);
		dest.writeParcelable(this.user, flags);
		dest.writeValue(this.dynamicId);
		dest.writeValue(this.UserId);
		dest.writeSerializable(this.releaseTime);
	}

	protected Zan(Parcel in) {
		this.zanId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.user = in.readParcelable(User.class.getClassLoader());
		this.dynamicId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.UserId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.releaseTime = (Timestamp) in.readSerializable();
	}

	public static final Parcelable.Creator<Zan> CREATOR = new Parcelable.Creator<Zan>() {
		@Override
		public Zan createFromParcel(Parcel source) {
			return new Zan(source);
		}

		@Override
		public Zan[] newArray(int size) {
			return new Zan[size];
		}
	};
}
