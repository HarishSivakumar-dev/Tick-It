package com.harish.TickIt.UserService.dtos;

public class TicketUpdateDto
{
	private int ticketId;
	private String status;
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
