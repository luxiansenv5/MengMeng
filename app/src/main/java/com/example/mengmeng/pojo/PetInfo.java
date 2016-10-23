package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PetInfo implements Parcelable {

	public Integer petId;
	public Integer userId;
	public String petName;
	public String petType;
	public Integer petAge;
	public String petPhoto;
	public Boolean petSex;
	public String petKind;
	
	public PetInfo(){
		
	}
	public PetInfo(String petName,String petKind){

		this.petName = petName;
		this.petKind = petKind;
	}
	
	public PetInfo(Integer petId, Integer userId, String petName,
			String petType, Integer petAge, String petPhoto, Boolean petSex,
			String petKind) {
		super();
		this.petId = petId;
		this.userId = userId;
		this.petName = petName;
		this.petType = petType;
		this.petAge = petAge;
		this.petPhoto = petPhoto;
		this.petSex = petSex;
		this.petKind = petKind;
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
		dest.writeValue(this.petAge);
		dest.writeString(this.petPhoto);
		dest.writeValue(this.petSex);
		dest.writeString(this.petKind);
	}

	protected PetInfo(Parcel in) {
		this.petId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.petName = in.readString();
		this.petType = in.readString();
		this.petAge = (Integer) in.readValue(Integer.class.getClassLoader());
		this.petPhoto = in.readString();
		this.petSex = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.petKind = in.readString();
	}

	public static final Parcelable.Creator<PetInfo> CREATOR = new Parcelable.Creator<PetInfo>() {
		@Override
		public PetInfo createFromParcel(Parcel source) {
			return new PetInfo(source);
		}

		@Override
		public PetInfo[] newArray(int size) {
			return new PetInfo[size];
		}
	};
}
