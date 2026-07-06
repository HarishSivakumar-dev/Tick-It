package com.harish.TickIt.TicketService.dtos;

import com.harish.TickIt.TicketService.enums.TicketStatus;

public class TicketStatusUpdateDto
{
	private int ticketId;
	private TicketStatus status;
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
}
