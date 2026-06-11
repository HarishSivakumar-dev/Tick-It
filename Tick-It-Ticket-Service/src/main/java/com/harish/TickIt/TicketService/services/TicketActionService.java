package com.harish.TickIt.TicketService.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.dtos.UserDetailsDto;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.feign.UserProfileFeignClient;
import com.harish.TickIt.TicketService.model.Ticket;
import com.harish.TickIt.TicketService.repos.TicketRepo;

@Service
public class TicketActionService
{	// This service will handle all the actions related to tickets such as creating a ticket, Getting a ticket, deleting a ticket, etc.
	
	@Autowired
	private UserProfileFeignClient userProfileFeignClient;
	@Autowired
	private TicketRepo ticketRepo;
	
	public String createTicket(TicketDetailsDto dto)
	{
		Ticket ticket = new Ticket();
		ticket.setTitle(dto.getTitle());
		ticket.setDescription(dto.getDescription());
		ticket.setStatus(TicketStatus.OPEN);
		ticket.setAssignedTo(null);
		ticket.setCreatedAt(java.time.LocalDateTime.now());
		ticket.setUpdatedAt(null);
		ticket.setClosedAt(null);
		ticket.setPriority(dto.getPriority());
		ticket.setProjectId(dto.getProjectId());
		
		UserDetailsDto userDetails = userProfileFeignClient.getUserProfile().getBody();
		
		ticket.setCreatedBy(userDetails.getName());
		ticket.setCreatorId(userDetails.getEmployeeId());
		ticket.setCreatorMail(userDetails.getEmail());
		ticket.setCreatorProfilePictureUrl(userDetails.getProfilePictureUrl());
		
		ticketRepo.save(ticket);
		
		return "Ticket created successfully";
	}
	
	public List<TicketResponseDto> getProjectTickets(int projectId)
	{
		// This method will return all the tickets of a project
		List<Ticket> tickets = ticketRepo.findByProjectIdAndStatusNotAndAssignedToIsNull(projectId, TicketStatus.RESOLVED);
		List<TicketResponseDto> responseDtos = tickets.stream()
													  .map(ticket -> {
														  				TicketResponseDto responseDto = new TicketResponseDto();
														  				responseDto.setId(ticket.getId());
														  				responseDto.setTitle(ticket.getTitle());
														  				responseDto.setDescription(ticket.getDescription());
														  				responseDto.setStatus(ticket.getStatus().toString());
														  				responseDto.setPriority(ticket.getPriority().toString());
														  				responseDto.setCreatedAt(ticket.getCreatedAt());
														  				responseDto.setUpdatedAt(ticket.getUpdatedAt());
														  				responseDto.setClosedAt(ticket.getClosedAt());
														  				responseDto.setCreatedBy(ticket.getCreatedBy());
														  				responseDto.setCreatorId(ticket.getCreatorId());
														  				responseDto.setCreatorMail(ticket.getCreatorMail());
														  				responseDto.setCreatorProfilePictureUrl(ticket.getCreatorProfilePictureUrl());
														
														  				return responseDto;
													  	 })
													 .toList();
		
		return responseDtos;
	}

	public String deleteTicket(int ticketId)
	{
		// This method will delete a ticket
		if(ticketRepo.existsById(ticketId))
		{
			ticketRepo.deleteById(ticketId);
			return "Ticket deleted successfully";
		}
		else
		{
			return "Ticket not found";
		}
	}
	
	public String updateTicketStatus(int ticketId, TicketStatus status)
	{
		// This method will update the status of a ticket
		Ticket ticket = ticketRepo.findById(ticketId).orElse(null);
		if(ticket != null)
		{
			ticket.setStatus(status);
			ticket.setUpdatedAt(java.time.LocalDateTime.now());
			
			if(status == TicketStatus.RESOLVED)
			{
				ticket.setClosedAt(java.time.LocalDateTime.now());
			}
			else if(status == TicketStatus.IN_PROGRESS)
			{
				ticket.setAssignedTo(userProfileFeignClient.getUserProfile().getBody().getName());
			}
			else if(status == TicketStatus.REOPENED)
			{
				ticket.setAssignedTo(null);
				ticket.setClosedAt(null);
			}
			
			ticketRepo.save(ticket);
			
			return "Ticket status updated successfully";
		}
		else
		{
			return "Ticket not found";
		}
	}

}
