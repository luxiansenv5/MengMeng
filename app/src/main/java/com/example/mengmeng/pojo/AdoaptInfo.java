package com.example.mengmeng.pojo;

import java.util.Date;

public class AdoaptInfo {

	private Integer userId;
	private Integer petId;
	private String describe;
	private String petImage;
	private boolean state;
	private Date releaseTime;
	
	public AdoaptInfo(){
		
	}
	
	public AdoaptInfo(Integer userId, Integer petId,
			String describe, String petImage, boolean state, Date releaseTime) {
		super();
		this.userId = userId;
		this.petId = petId;
		this.describe = describe;
		this.petImage = petImage;
		this.state = state;
		this.releaseTime = releaseTime;
	}

	public AdoaptInfo(Integer userId, Integer petId, String describle, Boolean state, Date releaseTime) {
		this.userId = userId;
		this.petId = petId;
		this.describe = describle;
		this.state = state;
		this.releaseTime = releaseTime;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPetImage() {
		return petImage;
	}
	public void setPetImage(String petImage) {
		this.petImage = petImage;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	
}
