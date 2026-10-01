package com.harish.tickit.notificationService.events;

import com.harish.tickit.notificationService.dtos.TicketCreationDto;

public class TicketCreationEvent
{
	private String message;
	private TicketCreationDto details;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketCreationDto getDetails() {
		return details;
	}
	public void setDetails(TicketCreationDto details) {
		this.details = details;
	}
	

}
