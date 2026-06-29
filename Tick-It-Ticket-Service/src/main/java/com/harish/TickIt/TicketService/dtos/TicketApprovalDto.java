package com.harish.TickIt.TicketService.dtos;

import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;

public class TicketApprovalDto
{
	private int ticketId;
	private TicketApprovalStatus status;
	private String description;
	
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public TicketApprovalStatus getStatus() {
		return status;
	}
	public void setStatus(TicketApprovalStatus status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
