package com.example.mengmeng.pojo;

public class ContactsInfoBean {

		private PetInfo petInfo;
		private UserInfo userInfo;
		
		public ContactsInfoBean(){}
		
		public ContactsInfoBean(PetInfo petInfo, UserInfo userInfo) {
			super();
			this.petInfo = petInfo;
			this.userInfo = userInfo;
		}
		
		public PetInfo getPetInfo() {
			return petInfo;
		}
		public void setPetInfo(PetInfo petInfo) {
			this.petInfo = petInfo;
		}
		public UserInfo getUserInfo() {
			return userInfo;
		}
		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
		}
		
		
		
}