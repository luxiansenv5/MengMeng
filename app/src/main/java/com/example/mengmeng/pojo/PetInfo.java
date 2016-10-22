package com.example.mengmeng.pojo;

public class PetInfo {

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
}
