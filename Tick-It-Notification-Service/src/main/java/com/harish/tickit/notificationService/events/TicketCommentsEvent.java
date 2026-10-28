package com.harish.tickit.notificationService.events;

import com.harish.tickit.notificationService.dtos.TicketCommentsDto;

public class TicketCommentsEvent
{
	private String message;
	private TicketCommentsDto details;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketCommentsDto getDetails() {
		return details;
	}
	public void setDetails(TicketCommentsDto details) {
		this.details = details;
	}
	
}
