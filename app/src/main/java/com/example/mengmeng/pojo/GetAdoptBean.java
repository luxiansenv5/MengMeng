package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class GetAdoptBean implements Parcelable {

	private Integer petId;
	private Integer userId;
	private String petName;
	private String petType;
	private String petPhoto;
	private Timestamp releaseTime;
	private Integer petAge;
	
	public GetAdoptBean(){
		
	}
	
	public GetAdoptBean(Integer petId, Integer userId, String petName,
			String petType, String petPhoto, Timestamp releaseTime,
			Integer petAge) {
		super();
		this.petId = petId;
		this.userId = userId;
		this.petName = petName;
		this.petType = petType;
		this.petPhoto = petPhoto;
		this.releaseTime = releaseTime;
		this.petAge = petAge;
	}
	
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public String getPetPhoto() {
		return petPhoto;
	}
	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
	}
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Integer getPetAge() {
		return petAge;
	}
	public void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.petId);
		dest.writeValue(this.userId);
		dest.writeString(this.petName);
		dest.writeString(this.petType);
		dest.writeString(this.petPhoto);
		dest.writeSerializable(this.releaseTime);
		dest.writeValue(this.petAge);
	}

	protected GetAdoptBean(Parcel in) {
		this.petId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.petName = in.readString();
		this.petType = in.readString();
		this.petPhoto = in.readString();
		this.releaseTime = (Timestamp) in.readSerializable();
		this.petAge = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Parcelable.Creator<GetAdoptBean> CREATOR = new Parcelable.Creator<GetAdoptBean>() {
		@Override
		public GetAdoptBean createFromParcel(Parcel source) {
			return new GetAdoptBean(source);
		}

		@Override
		public GetAdoptBean[] newArray(int size) {
			return new GetAdoptBean[size];
		}
	};
}
