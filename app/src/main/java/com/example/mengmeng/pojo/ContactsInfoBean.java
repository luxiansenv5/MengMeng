package com.example.mengmeng.pojo;

public class ContactsInfoBean {

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
}