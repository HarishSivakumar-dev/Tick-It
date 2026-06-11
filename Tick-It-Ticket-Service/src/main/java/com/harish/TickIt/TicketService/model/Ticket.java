package com.harish.TickIt.TicketService.model;

import java.time.LocalDateTime;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket
{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	private String creatorProfilePictureUrl;
	private String title;
	private String description;
	private TicketStatus status;
	private String assignedTo;
	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime closedAt;
	private TicketPriority priority;
	private long projectId;
	private long creatorId;
	private String creatorMail;
	
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
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public TicketPriority getPriority() {
		return priority;
	}
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorProfilePictureUrl() {
		return creatorProfilePictureUrl;
	}
	public void setCreatorProfilePictureUrl(String creatorProfilePictureUrl) {
		this.creatorProfilePictureUrl = creatorProfilePictureUrl;
	}
	public String getCreatorMail() {
		return creatorMail;
	}
	public void setCreatorMail(String creatorMail) {
		this.creatorMail = creatorMail;
	}

}
