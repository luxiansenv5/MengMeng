package com.example.mengmeng.pojo;

public class ContactsInfo {
	
	private Integer contactId;
	private Integer userId;
	
	public ContactsInfo(){}
	
	public ContactsInfo(Integer contactId, Integer userId) {
		super();
		this.contactId = contactId;
		this.userId = userId;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
