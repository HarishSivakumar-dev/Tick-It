package com.harish.TickIt.TicketService.wrapperimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.harish.TickIt.TicketService.dtos.TicketAvailDto;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.dtos.UserDetailsDto;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.feign.ProjectFeignClient;
import com.harish.TickIt.TicketService.feign.UserProfileFeignClient;
import com.harish.TickIt.TicketService.model.Ticket;

@Component
public class TicketWrapperImpl implements com.harish.TickIt.TicketService.wrapper.TicketWrappers
{
	
	@Autowired
	private UserProfileFeignClient userProfileFeignClient;
	@Autowired
	private ProjectFeignClient projectFeignClient;


	@Override
	public Ticket createTicket(TicketDetailsDto dto)
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
		
		Optional<TicketAvailDto> isPre= projectFeignClient.getProjectIdFromService(dto.getProjectId());
		
		if(isPre.isEmpty())
		{
			throw new RuntimeException("NO PROJECT ID FOUND");
		}
		else
		{
			if(!isPre.get().isActive())
			{
				throw new RuntimeException("PROJECT EXPIRED !");
			}
			ticket.setProjectId(isPre.get().getProjectId());
		}
		
		UserDetailsDto userDetails = userProfileFeignClient.getUserProfile().getBody();
		
		ticket.setCreatedBy(userDetails.getName());
		ticket.setCreatorId(userDetails.getEmployeeId());
		ticket.setCreatorMail(userDetails.getEmail());
		ticket.setCreatorProfilePictureUrl(userDetails.getProfilePictureUrl());
		
		return ticket;
	}


	@Override
	public TicketResponseDto toDto(Ticket ticket)
	{
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
	}

}
