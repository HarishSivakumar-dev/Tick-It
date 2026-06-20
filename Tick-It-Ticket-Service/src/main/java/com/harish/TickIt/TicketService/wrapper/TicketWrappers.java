package com.harish.TickIt.TicketService.wrapper;

import org.springframework.stereotype.Component;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.model.Ticket;

@Component
public interface TicketWrappers
{
	public Ticket createTicket(com.harish.TickIt.TicketService.dtos.TicketDetailsDto dto);
	
	public TicketResponseDto toDto(Ticket ticket);

}
