package com.harish.TickIt.TicketService.dtos;

import com.harish.TickIt.TicketService.enums.TicketStatus;

public class TicketStatusUpdateDto
{
	private int ticketId;
	private TicketStatus ticketStatus;
	private Long userId;
	private long projectId;
	
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

}
