package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.List;

public class Dynamic implements Parcelable {

	public Integer dynamicId;
	public Timestamp releaseTime;//发布时间
	public String releaseText;//发布内容
	public String picture;//发布照片
	public String place;//地点
	public User  user;
	public List<Zan> zan;//点赞人
	public List<Remark> remarklist;//评论list

	public Dynamic(Integer dynamicId, Timestamp releaseTime,
				   String releaseText, String picture, String place, User user,
				   List<Zan> zan) {
		super();
		this.dynamicId = dynamicId;
		this.releaseTime = releaseTime;
		this.releaseText = releaseText;
		this.picture = picture;
		this.place = place;
		this.user = user;
		this.zan = zan;
		}
	public Dynamic(){
		
	}
	public Dynamic(String place){
		this.place = place;
	}
	
	public Dynamic(Integer dynamicId, Timestamp releaseTime,
			String releaseText, String picture, String place, User user) {
		super();
		this.dynamicId = dynamicId;
		this.releaseTime = releaseTime;
		this.releaseText = releaseText;
		this.picture = picture;
		this.place = place;
		this.user = user;
	}

	public Integer getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseText() {
		return releaseText;
	}

	public void setReleaseText(String releaseText) {
		this.releaseText = releaseText;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Zan> getZan() {
		return zan;
	}

	public void setZan(List<Zan> zan) {
		this.zan = zan;
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.dynamicId);
		dest.writeSerializable(this.releaseTime);
		dest.writeString(this.releaseText);
		dest.writeString(this.picture);
		dest.writeString(this.place);
		dest.writeParcelable(this.user, flags);
		dest.writeTypedList(this.zan);
	}

	protected Dynamic(Parcel in) {
		this.dynamicId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.releaseTime = (Timestamp) in.readSerializable();
		this.releaseText = in.readString();
		this.picture = in.readString();
		this.place = in.readString();
		this.user = in.readParcelable(User.class.getClassLoader());
		this.zan = in.createTypedArrayList(Zan.CREATOR);
	}

	public static final Creator<Dynamic> CREATOR = new Creator<Dynamic>() {
		@Override
		public Dynamic createFromParcel(Parcel source) {
			return new Dynamic(source);
		}

		@Override
		public Dynamic[] newArray(int size) {
			return new Dynamic[size];
		}
	};
}
