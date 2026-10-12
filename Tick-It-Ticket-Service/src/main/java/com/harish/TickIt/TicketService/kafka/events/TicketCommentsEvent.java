package com.harish.TickIt.TicketService.kafka.events;

import com.harish.TickIt.TicketService.dtos.TicketCommentPayloadDto;

public class TicketCommentsEvent
{
	private String eventType; 
	private TicketCommentPayloadDto payload;
	
	
	public TicketCommentsEvent(String eventType, TicketCommentPayloadDto payload) {
		super();
		this.eventType = eventType;
		this.payload = payload;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public TicketCommentPayloadDto getPayload() {
		return payload;
	}
	public void setPayload(TicketCommentPayloadDto payload) {
		this.payload = payload;
	}
}
