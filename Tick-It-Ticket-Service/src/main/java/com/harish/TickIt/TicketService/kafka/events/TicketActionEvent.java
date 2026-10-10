package com.harish.TickIt.TicketService.kafka.events;

import com.harish.TickIt.TicketService.dtos.TicketActionDto;

public class TicketActionEvent 
{
	private String message;
	private TicketActionDto details;
	
	public TicketActionEvent(String message, TicketActionDto details) {
		super();
		this.message = message;
		this.details = details;
	}
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
