package com.harish.TickIt.TicketService.dtos;


public class AssignUserDto
{
	
	private int userId;
	private int ticketId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	

}
