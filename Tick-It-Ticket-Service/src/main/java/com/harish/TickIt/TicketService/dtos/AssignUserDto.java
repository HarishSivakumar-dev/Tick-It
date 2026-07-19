package com.harish.TickIt.TicketService.dtos;


public class AssignUserDto
{
	
	private Long employeeId;
	private int ticketId;
	
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	

}
