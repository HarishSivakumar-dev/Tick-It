package com.harish.TickIt.TicketService.kafka.events;

import com.harish.TickIt.TicketService.dtos.TicketUpdationDto;

public class TicketUpdatedEvent 
{
	private String message;
	private TicketUpdationDto data;
	
	
	public TicketUpdatedEvent(String message, TicketUpdationDto data) {
		super();
		this.message = message;
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketUpdationDto getData() {
		return data;
	}
	public void setData(TicketUpdationDto data) {
		this.data = data;
	}
	
	

}
