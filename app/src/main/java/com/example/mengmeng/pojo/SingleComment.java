package com.example.mengmeng.pojo;

import java.sql.Timestamp;

public class SingleComment {

	private Integer commentId;
	private Integer publisherId;
	private Integer commentatorId;
	private Integer releaseId;
	private String content;
	private Timestamp commentTime;
	private Integer commentType;
	private String commentatorName;
	private String  commentatorPhoto;
	
	public SingleComment(){}

	public SingleComment(Integer publisherId, Integer commentatorId, Integer releaseId, String content, Integer commentType,String commentatorPhoto,String commentatorName) {
		this.publisherId = publisherId;
		this.commentatorId = commentatorId;
		this.releaseId = releaseId;
		this.content = content;
		this.commentType = commentType;
		this.commentatorPhoto=commentatorPhoto;
		this.commentatorName=commentatorName;
	}

	public SingleComment(Integer commentId, Integer publisherId,
						 Integer commentatorId, Integer releaseId, String content,
						 Timestamp commentTime, Integer commentType, String commentatorName,
						 String commentatorPhoto) {
		super();
		this.commentId = commentId;
		this.publisherId = publisherId;
		this.commentatorId = commentatorId;
		this.releaseId = releaseId;
		this.content = content;
		this.commentTime = commentTime;
		this.commentType = commentType;
		this.commentatorName = commentatorName;
		this.commentatorPhoto = commentatorPhoto;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getCommentatorId() {
		return commentatorId;
	}

	public void setCommentatorId(Integer commentatorId) {
		this.commentatorId = commentatorId;
	}

	public Integer getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public String getCommentatorName() {
		return commentatorName;
	}

	public void setCommentatorName(String commentatorName) {
		this.commentatorName = commentatorName;
	}

	public String getCommentatorPhoto() {
		return commentatorPhoto;
	}

	public void setCommentatorPhoto(String commentatorPhoto) {
		this.commentatorPhoto = commentatorPhoto;
	}
	
}
