package com.example.mengmeng.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactsInfoBean implements Parcelable {

		private PetInfo petInfo;
		private User userInfo;

	public ContactsInfoBean() {}
	public ContactsInfoBean(PetInfo petInfo, User user) {
		this.petInfo = petInfo;
		this.userInfo = user;
	}

	public User getUser() {
		return userInfo;
	}

	public void setUser(User user) {
		this.userInfo = user;
	}

	public PetInfo getPetInfo() {
		return petInfo;
	}

	public void setPetInfo(PetInfo petInfo) {
		this.petInfo = petInfo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.petInfo, flags);
		dest.writeParcelable(this.userInfo, flags);
	}

	protected ContactsInfoBean(Parcel in) {
		this.petInfo = in.readParcelable(PetInfo.class.getClassLoader());
		this.userInfo = in.readParcelable(User.class.getClassLoader());
	}

	public static final Parcelable.Creator<ContactsInfoBean> CREATOR = new Parcelable.Creator<ContactsInfoBean>() {
		@Override
		public ContactsInfoBean createFromParcel(Parcel source) {
			return new ContactsInfoBean(source);
		}

		@Override
		public ContactsInfoBean[] newArray(int size) {
			return new ContactsInfoBean[size];
		}
	};
}