package com.example.mengmeng.pojo;

import java.sql.Timestamp;

public class Remark {

	public Integer remarkId;
	public  String remarkContent;
	public Timestamp remarkTime;
	public User user;
	public Boolean isEnd;
	public Integer fatherRemarkId;
	public Integer dynamicId;
	public User fatherUser;
	
	public Remark(){
		
	}

	public Remark(User user, String remarkContent, Timestamp remarkTime, User fatherUser) {
		this.user = user;
		this.remarkContent = remarkContent;
		this.remarkTime = remarkTime;
		this.fatherUser = fatherUser;
	}

	public Remark(User user, Timestamp remarkTime, String remarkContent) {
		this.user = user;
		this.remarkTime = remarkTime;
		this.remarkContent = remarkContent;
	}

	public Remark(Integer remarkId, String remarkContent, Timestamp remarkTime,
				  User user, Boolean isEnd, Integer fatherRemarkId, Integer dynamicId, User fatherUser) {
		super();
		this.remarkId = remarkId;
		this.remarkContent = remarkContent;
		this.remarkTime = remarkTime;
		this.user = user;
		this.isEnd = isEnd;
		this.fatherRemarkId = fatherRemarkId;
		this.dynamicId=dynamicId;
		this.fatherUser = fatherUser;
	}
	
	

	public Remark(Integer remarkId, String remarkContent, Timestamp remarkTime,
			User user, Boolean isEnd,Integer dynamicId) {
		super();
		this.remarkId = remarkId;
		this.remarkContent = remarkContent;
		this.remarkTime = remarkTime;
		this.user = user;
		this.isEnd = isEnd;
		this.dynamicId = dynamicId;
	}

	public Integer getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemarkContent() {
		return remarkContent;
	}

	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}

	public Timestamp getRemarkTime() {
		return remarkTime;
	}

	public void setRemarkTime(Timestamp remarkTime) {
		this.remarkTime = remarkTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}

	public Integer getFatherRemarkId() {
		return fatherRemarkId;
	}

	public Integer getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}

	public void setFatherRemarkId(Integer fatherRemarkId) {
		this.fatherRemarkId = fatherRemarkId;
	}

	public User getFatherUser() {
		return fatherUser;
	}

	public void setFatherUser(User fatherUser) {
		this.fatherUser = fatherUser;
	}
	
	
	
	
}
