package com.harish.tickit.notificationService.events;

import com.harish.tickit.notificationService.dtos.TicketActionDto;

public class TicketActionEvent
{
	private String message;
	private TicketActionDto details;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketActionDto getDetails() {
		return details;
	}
	public void setDetails(TicketActionDto details) {
		this.details = details;
	}
	

}
