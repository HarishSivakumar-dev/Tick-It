package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.auth.UserPrincipal;
import com.harish.TickIt.TicketService.dtos.AssignUserDto;
import com.harish.TickIt.TicketService.dtos.TicketApprovalDto;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.dtos.TicketStatusUpdateDto;
import com.harish.TickIt.TicketService.dtos.UserFeignDto;
import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.feign.ProjectFeignClient;
import com.harish.TickIt.TicketService.feign.UserFeignClient;
import com.harish.TickIt.TicketService.model.Ticket;
import com.harish.TickIt.TicketService.model.TicketApprovalAudit;
import com.harish.TickIt.TicketService.repos.TicketApprovalAuditRepo;
import com.harish.TickIt.TicketService.repos.TicketRepo;
import com.harish.TickIt.TicketService.wrapperimpl.TicketWrapperImpl;
import jakarta.transaction.Transactional;


@Service
public class TicketActionService
{	// This service will handle all the actions related to tickets such as creating a ticket, Getting a ticket, deleting a ticket, etc.
	
	@Autowired
	private TicketRepo ticketRepo;
	@Autowired
	private TicketWrapperImpl ticketWrapperImpl;
	@Autowired
	private ProjectFeignClient pfc;
	@Autowired
	private UserFeignClient ufc;
	@Autowired
	private TicketApprovalAuditRepo trep;
	@Autowired
	private RedisTemplate<String,List<TicketResponseDto>> redisTemplate; 
	
	public String createTicket(TicketDetailsDto dto)
	{
		// This method will create a ticket
		Ticket ticket = ticketWrapperImpl.createTicket(dto);
		ticketRepo.save(ticket);
		
		redisTemplate.delete("projectTickets:"+dto.getProjectId());
		redisTemplate.delete("AvailableTickets:"+dto.getProjectId());
		redisTemplate.delete("AllTickets:"+dto.getProjectId());

		
		return "Ticket created successfully";
	}
	
	public List<TicketResponseDto> getProjectTickets(int projectId)
	{
		// This method will return all the tickets of a project
		List<TicketResponseDto> tk= redisTemplate.opsForValue().get("projectTickets:"+projectId);
		
		if(tk==null)
		{
			List<Ticket> tickets = ticketRepo.findByProjectIdAndStatusNotAndAssignedToIsNull(projectId, TicketStatus.RESOLVED);
			List<TicketResponseDto> responseDtos = tickets.stream()
														  .map(ticket -> {
															  				TicketResponseDto responseDto = ticketWrapperImpl.toDto(ticket);
															  				return responseDto;
														  			 	})	
														 .toList();
			
			redisTemplate.opsForValue().set("projectTickets:"+projectId, responseDtos, 10, TimeUnit.MINUTES);
			return responseDtos;
		}
		else
		{
			return tk;
		}
	}

	public String deleteTicket(int ticketId, long projectId)
	{
		// This method will delete a ticket
		if(ticketRepo.existsById(ticketId))
		{
			ticketRepo.deleteById(ticketId);
			
			redisTemplate.delete("projectTickets:"+projectId);
			redisTemplate.delete("AvailableTickets:"+ projectId);
			redisTemplate.delete("AllTickets:"+projectId);

			return "Ticket deleted successfully";
		}
		else
		{
			return "Ticket not found";
		}
	}
	
	public String updateTicketStatus(TicketStatusUpdateDto dto)
	{
		// This method will update the status of a ticket
		Ticket ticket = ticketRepo.findById(dto.getTicketId()).orElse(null);
		if(ticket != null)
		{
			TicketStatus status=dto.getStatus();
			ticket.setStatus(status);
			ticket.setUpdatedAt(java.time.LocalDateTime.now());
			
			if(status == TicketStatus.RESOLVED)
			{
				ticket.setClosedAt(java.time.LocalDateTime.now());
				ticket.setApproved(TicketApprovalStatus.PENDING);
			}
			else if(status == TicketStatus.REOPENED)
			{
				ticket.setAssignedTo(null);
				ticket.setClosedAt(null);
				redisTemplate.delete("userTickets:"+ticket.getAssignedEmployeeId());
				ticket.setAssignedEmployeeId(null);
			}
			
			ticketRepo.save(ticket);
			
			redisTemplate.delete("projectTickets:"+ticket.getProjectId());
			redisTemplate.delete("AvailableTickets:"+ticket.getProjectId());
			redisTemplate.delete("AllTickets:"+ticket.getProjectId());

			
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
		
		UserFeignDto ufr=  ufc.getUserFromAuthService(Long.valueOf(dto.getEmployeeId())).getBody();
		
		if(tk.getAssignedEmployeeId()!=null)
		{
			throw new RuntimeException("Already Assigned !");
		}
		
		tk.setAssignedTo(ufr.getUserName());
		tk.setUpdatedAt(LocalDateTime.now());
		tk.setAssignedEmployeeId(ufr.getEmployeeId());
		tk.setStatus(TicketStatus.IN_PROGRESS);
		
		ticketRepo.save(tk);
		
		redisTemplate.delete("projectTickets:"+tk.getProjectId());
		redisTemplate.delete("AvailableTickets:"+tk.getProjectId());
		redisTemplate.delete("userTickets:"+tk.getAssignedEmployeeId());
		redisTemplate.delete("AllTickets:"+tk.getProjectId());
		
		return "User Assigned !";
	}
	
	public List<TicketResponseDto> getAvailableTicketsForUser(int projectId)
	{
		
		List<TicketResponseDto> res= redisTemplate.opsForValue().get("AvailableTickets:"+projectId);
		
		if(res==null)
		{
			List<TicketPriority> ls= new ArrayList<>();
			ls.add(TicketPriority.CRITICAL);
			ls.add(TicketPriority.HIGH);
		
			List<TicketStatus> stat= new ArrayList<>();
			stat.add(TicketStatus.OPEN);
			stat.add(TicketStatus.REOPENED);
			
			List<TicketResponseDto> dt= ticketRepo.findByProjectIdAndStatusInAndPriorityNotInAndAssignedToIsNull(projectId,stat,ls)
												  .stream()
												  .map(r->{
													  return ticketWrapperImpl.toDto(r);
												  })
												  .toList();
			
			redisTemplate.opsForValue().set("AvailableTickets:"+projectId, dt,10, TimeUnit.MINUTES);
			return dt;
		}
		else
		{
			return res;
		}
		
	}
	
	@Transactional
	public String updateTicketApprovalStatus(TicketApprovalDto dto)
	{
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(dto.getStatus().equals(TicketApprovalStatus.PENDING))
		{
			return "Same State !"; 
		}
		
		Ticket tk= ticketRepo.findById(dto.getTicketId()).orElseThrow(()-> new RuntimeException("No Ticket Found !"));
		
		if(dto.getStatus().equals(TicketApprovalStatus.NOT_APPROVED))
		{
			tk.setApproved(null);
			tk.setClosedAt(null);
			tk.setStatus(TicketStatus.REOPENED);
			
			redisTemplate.delete("userTickets:"+tk.getAssignedEmployeeId());
			
			tk.setAssignedEmployeeId(null);
			tk.setAssignedTo(null);
			
			redisTemplate.delete("AvailableTickets:"+tk.getProjectId());
		}
		else
		{
			tk.setApproved(dto.getStatus());
		}
		
		tk.setUpdatedAt(LocalDateTime.now());
		
		TicketApprovalAudit taa= new TicketApprovalAudit();
		taa.setDescription(dto.getDescription());
		taa.setHandledUserName(up.getUserName());
		taa.setStatus(dto.getStatus());
		taa.setTicketId(tk);
		
		trep.save(taa);
		
		redisTemplate.delete("projectTickets:"+tk.getProjectId());
		redisTemplate.delete("AllTickets:"+tk.getProjectId());
		
		return "Updated Successfully";
	}
	
	public List<TicketResponseDto> getTicketBasedOnFilter(TicketStatus stat, TicketPriority prior, TicketApprovalStatus status)
	{
		List<TicketResponseDto> res= ticketRepo.filterBySpecs(stat, prior, status)
											   .stream()
											   .map(r->{
												   return ticketWrapperImpl.toDto(r);
											   })
											   .toList();
		return res;
	}
	
	public List<TicketResponseDto> getUserTickets()
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<TicketResponseDto> dt=redisTemplate.opsForValue().get("userTickets:"+prin.getEmployeeId());
		
		if(dt==null)
		{
			List<TicketResponseDto> res= ticketRepo.findByAssignedEmployeeIdOrderByCreatedAtDesc((long)prin.getEmployeeId())
					   .stream()
					   .map(r->{
						   return ticketWrapperImpl.toDto(r);
					   })
					   .toList();
			
			redisTemplate.opsForValue().set("userTickets:"+prin.getEmployeeId(), res, 10, TimeUnit.MINUTES);
			
			return res;
		}
		else
		{
			return dt;
		}
	}
	
	public List<TicketResponseDto> getAllTickets(int projectId)
	{	
		if(pfc.verifyEmployee(projectId).getBody())
		{
			List<TicketResponseDto> rs= redisTemplate.opsForValue().get("AllTickets:"+projectId);
			if(rs==null)
			{
				List<TicketResponseDto> res= ticketRepo.findByProjectId(projectId)
						   .stream()
						   .map(r->{
							   return ticketWrapperImpl.toDto(r);
						   })
						   .toList();
				
				redisTemplate.opsForValue().set("AllTickets:"+projectId,res,10,TimeUnit.MINUTES);
				return res;
			}
			else
			{
				return rs;
			}
		}
		else
		{
			throw new RuntimeException("UNAUTHORIZED");
		}
		
	}
	
	public List<TicketResponseDto> getUnapprovedTickets(int projectid, TicketApprovalStatus status)
	{	
		if(pfc.verifyEmployee(projectid).getBody())
		{
			List<TicketResponseDto> dto= ticketRepo.findByProjectIdAndApproved(projectid,status)
					  .stream()
					  .map(r->{
						  return ticketWrapperImpl.toDto(r);
					  })
					  .toList();
			return dto;
		}
		else
		{
			throw new RuntimeException("UNAUTHORIZED");
		}
	}
	
}
