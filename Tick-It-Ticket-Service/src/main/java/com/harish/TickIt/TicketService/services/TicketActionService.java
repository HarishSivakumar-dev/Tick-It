package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.dtos.AssignUserDto;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.dtos.TicketStatusUpdateDto;
import com.harish.TickIt.TicketService.dtos.UserFeignDto;
import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.feign.UserFeignClient;
import com.harish.TickIt.TicketService.feign.UserProfileFeignClient;
import com.harish.TickIt.TicketService.model.Ticket;
import com.harish.TickIt.TicketService.repos.TicketRepo;
import com.harish.TickIt.TicketService.wrapperimpl.TicketWrapperImpl;


@Service
public class TicketActionService
{	// This service will handle all the actions related to tickets such as creating a ticket, Getting a ticket, deleting a ticket, etc.
	
	@Autowired
	private TicketRepo ticketRepo;
	@Autowired
	private TicketWrapperImpl ticketWrapperImpl;
	@Autowired
	private UserProfileFeignClient userProfileFeignClient;
	@Autowired
	private UserFeignClient ufc;
	
	public String createTicket(TicketDetailsDto dto)
	{
		// This method will create a ticket
		Ticket ticket = ticketWrapperImpl.createTicket(dto);
		ticketRepo.save(ticket);
		
		return "Ticket created successfully";
	}
	
	public List<TicketResponseDto> getProjectTickets(int projectId)
	{
		// This method will return all the tickets of a project
		List<Ticket> tickets = ticketRepo.findByProjectIdAndStatusNotAndAssignedToIsNull(projectId, TicketStatus.RESOLVED);
		List<TicketResponseDto> responseDtos = tickets.stream()
													  .map(ticket -> {
														  				TicketResponseDto responseDto = ticketWrapperImpl.toDto(ticket);
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
	
	public String assignUserTicket(AssignUserDto dto) throws Exception
	{
		Ticket tk= ticketRepo.findById(dto.getTicketId()).orElseThrow(()-> new Exception("TICKET NOT FOUND !"));
		
		UserFeignDto ufr=  ufc.getUserFromAuthService(Long.valueOf(dto.getUserId())).getBody();
		
		tk.setAssignedTo(ufr.getUserName());
		tk.setUpdatedAt(LocalDateTime.now());
		tk.setStatus(TicketStatus.IN_PROGRESS);
		
		ticketRepo.save(tk);
		
		return "User Assigned !";
	}
	
	public List<TicketResponseDto> getAvailableTicketsForUser(int projectId)
	{
		List<TicketPriority> ls= new ArrayList<>();
		ls.add(TicketPriority.CRITICAL);
		ls.add(TicketPriority.HIGH);
		
		List<TicketResponseDto> dt= ticketRepo.findByProjectIdAndStatusAndPriorityNotInAndAssignedToIsNull(projectId,TicketStatus.OPEN,ls)
											  .stream()
											  .map(r->{
												  
												  TicketResponseDto dto= new TicketResponseDto();
												  dto.setClosedAt(r.getClosedAt());
												  dto.setCreatedAt(r.getCreatedAt());
												  dto.setCreatedBy(r.getCreatedBy());
												  dto.setCreatorId(r.getCreatorId());
												  dto.setCreatorMail(r.getCreatorMail());
												  dto.setCreatorProfilePictureUrl(r.getCreatorProfilePictureUrl());
												  dto.setDescription(r.getDescription());
												  dto.setId(r.getId());
												  dto.setPriority(r.getPriority().toString());
												  dto.setStatus(r.getStatus().toString());
												  dto.setTitle(r.getTitle());
												  dto.setUpdatedAt(r.getUpdatedAt());
												  
												  return dto;
												  
											  })
											  .toList();
		
		return dt;
	}

	public String updateTicketStatus(TicketStatusUpdateDto dto) throws Exception
	{
		Ticket tk= ticketRepo.findById(dto.getTicketId()).orElseThrow(()-> new Exception("NO TICKET FOUND"));
		
		tk.setStatus(dto.getTicketStatus());
		tk.setClosedAt(LocalDateTime.now());
		tk.setApproved(TicketApprovalStatus.PENDING);
		
		ticketRepo.save(tk);
		
		return "CHANGES UPDATED ";
	}

}
