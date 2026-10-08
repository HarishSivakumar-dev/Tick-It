package com.harish.TickIt.TicketService.dtos;

import java.time.LocalDateTime;

import com.harish.TickIt.TicketService.enums.TicketPriority;

public class TicketDeletionDto
{
	private String title;
	private TicketPriority priority;
	private Long projectId;
	private LocalDateTime deletionTime;
	
	public TicketDeletionDto(String title, TicketPriority priority, Long projectId, LocalDateTime deletionTime) {
		super();
		this.title = title;
		this.priority = priority;
		this.projectId = projectId;
		this.deletionTime = deletionTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TicketPriority getPriority() {
		return priority;
	}
	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public LocalDateTime getDeletionTime() {
		return deletionTime;
	}
	public void setDeletionTime(LocalDateTime deletionTime) {
		this.deletionTime = deletionTime;
	}
	
}
