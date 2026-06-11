package com.harish.TickIt.TicketService.dtos;

import com.harish.TickIt.TicketService.enums.TicketPriority;

public class TicketDetailsDto
{
	private String title;
	private String description;
	private TicketPriority priority;
	private long projectId;
	
	
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
	
	

}
