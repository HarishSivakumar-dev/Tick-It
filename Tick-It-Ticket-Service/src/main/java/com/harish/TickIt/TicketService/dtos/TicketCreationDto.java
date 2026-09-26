package com.harish.TickIt.TicketService.dtos;

import com.harish.TickIt.TicketService.enums.TicketPriority;

public class TicketCreationDto
{
	private String title;
	private TicketPriority priority;
	private Long projectId;
	
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
	
}
