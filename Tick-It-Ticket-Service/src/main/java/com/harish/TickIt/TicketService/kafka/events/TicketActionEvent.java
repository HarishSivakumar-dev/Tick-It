package com.harish.TickIt.TicketService.kafka.events;

import com.harish.TickIt.TicketService.dtos.TicketCreationDto;

public class TicketCreatedEvent 
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
