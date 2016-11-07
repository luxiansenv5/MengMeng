package com.example.mengmeng.pojo;

public class DetailsBean {

	private String petImage;
	private String petPhoto;
	private String petName;
	private String petType;
	private String describe;
	private String publisherPhoto;
	private String publisherName;
	private Integer releaseId;
	private Integer publisherId;
	
	public DetailsBean(){
		
	}

	public DetailsBean(String petImage, String petPhoto, String petName,
			String petType, String describe, String publisherPhoto,
			String publisherName, Integer releaseId, Integer publisherId) {
		super();
		this.petImage = petImage;
		this.petPhoto = petPhoto;
		this.petName = petName;
		this.petType = petType;
		this.describe = describe;
		this.publisherPhoto = publisherPhoto;
		this.publisherName = publisherName;
		this.releaseId = releaseId;
		this.publisherId = publisherId;
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

	public String getPublisherPhoto() {
		return publisherPhoto;
	}

	public void setPublisherPhoto(String publisherPhoto) {
		this.publisherPhoto = publisherPhoto;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Integer getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	
}
