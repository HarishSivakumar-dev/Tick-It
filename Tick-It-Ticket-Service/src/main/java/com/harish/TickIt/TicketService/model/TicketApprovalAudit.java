package com.harish.TickIt.TicketService.model;

import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TicketApprovalAudit 
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Ticket ticketId;
	
	private String handledUserName;
	
	@Enumerated(EnumType.STRING)
	private TicketApprovalStatus status;
	
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ticket getTicketId() {
		return ticketId;
	}

	public void setTicketId(Ticket ticketId) {
		this.ticketId = ticketId;
	}

	public TicketApprovalStatus getStatus() {
		return status;
	}

	public void setStatus(TicketApprovalStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHandledUserName() {
		return handledUserName;
	}

	public void setHandledUserName(String handledUserName) {
		this.handledUserName = handledUserName;
	}


}
