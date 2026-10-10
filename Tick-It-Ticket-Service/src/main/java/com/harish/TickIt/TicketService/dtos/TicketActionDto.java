package com.harish.TickIt.TicketService.dtos;

import java.time.LocalDateTime;

import com.harish.TickIt.TicketService.enums.TicketPriority;

public class TicketActionDto
{
	private String title;
	private TicketPriority priority;
	private Long projectId;
	private LocalDateTime timeStamp;
	
	public TicketActionDto(String title, TicketPriority priority, Long projectId, LocalDateTime timeStamp) {
		super();
		this.title = title;
		this.priority = priority;
		this.projectId = projectId;
		this.timeStamp = timeStamp;
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
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
