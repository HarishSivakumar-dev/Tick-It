package com.harish.TickIt.TicketService.kafka.events;

import com.harish.TickIt.TicketService.dtos.TicketDeletionDto;

public class TicketDeletedEvent 
{
	private String message;
	private TicketDeletionDto details;
	
	public TicketDeletedEvent(String message, TicketDeletionDto details)
	{
		this.message = message;
		this.details = details;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketDeletionDto getDetails() {
		return details;
	}
	public void setDetails(TicketDeletionDto details) {
		this.details = details;
	}

}
