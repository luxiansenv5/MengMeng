package com.example.mengmeng.pojo;

public class DetailsBean {

	private String petImage;
	private String petPhoto;
	private String petName;
	private String petType;
	private String describe;
	private String userPhoto;
	private String userName;
	
	public DetailsBean(){
		
	}
	
	public DetailsBean(String petImage, String petPhoto, String petName,
			String petType, String describe, String userPhoto, String userName) {
		super();
		this.petImage = petImage;
		this.petPhoto = petPhoto;
		this.petName = petName;
		this.petType = petType;
		this.describe = describe;
		this.userPhoto = userPhoto;
		this.userName = userName;
	}
	
	public String getPetImage() {
		return petImage;
	}
	public void setPetImage(String petImage) {
		this.petImage = petImage;
	}
	public String getPetPhoto() {
		return petPhoto;
	}
	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
