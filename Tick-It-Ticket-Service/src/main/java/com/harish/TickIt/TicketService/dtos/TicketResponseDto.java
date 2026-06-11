package com.harish.TickIt.TicketService.dtos;

import java.time.LocalDateTime;

public class TicketResponseDto 
{
	private int id;
	private String title;
	private String description;
	private String status;
	private String createdBy;
	private String creatorMail;
	private String creatorProfilePictureUrl;
	private String priority;
	private long creatorId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime closedAt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatorMail() {
		return creatorMail;
	}
	public void setCreatorMail(String creatorMail) {
		this.creatorMail = creatorMail;
	}
	public String getCreatorProfilePictureUrl() {
		return creatorProfilePictureUrl;
	}
	public void setCreatorProfilePictureUrl(String creatorProfilePictureUrl) {
		this.creatorProfilePictureUrl = creatorProfilePictureUrl;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDateTime getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}
	

}
