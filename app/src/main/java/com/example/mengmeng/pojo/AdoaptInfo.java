package com.example.mengmeng.pojo;

import java.sql.Timestamp;

public class AdoaptInfo {

	private Integer userId;
	private Integer petId;
	private String describe;
	private String petImage;
	private boolean state;
	private Timestamp releaseTime;
	
	public AdoaptInfo(){
		
	}
	
	public AdoaptInfo(Integer userId, Integer petId,
			String describe, String petImage, boolean state, Timestamp releaseTime) {
		super();
		this.userId = userId;
		this.petId = petId;
		this.describe = describe;
		this.petImage = petImage;
		this.state = state;
		this.releaseTime = releaseTime;
	}

	public AdoaptInfo(Integer userId, Integer petId, String describle) {
		this.userId = userId;
		this.petId = petId;
		this.describe = describle;
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
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	
}
