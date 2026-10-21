package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.auth.UserPrincipal;
import com.harish.TickIt.TicketService.dtos.AssignUserDto;
import com.harish.TickIt.TicketService.dtos.MemberDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketActionDto;
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
import com.harish.TickIt.TicketService.kafka.events.TicketActionEvent;
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
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	//refarctored, only pending is, Project Status should be fetched within one single Feign call, instead of multiple calls for each ticket.
	public String createTicket(TicketDetailsDto dto)
	{
		// This method will create a ticket
		Ticket ticket = ticketWrapperImpl.createTicket(dto);
		ticketRepo.save(ticket);
		
		redisTemplate.delete("projectTickets:"+dto.getProjectId());
		redisTemplate.delete("AvailableTickets:"+dto.getProjectId());
		redisTemplate.delete("AllTickets:"+dto.getProjectId());
		
		TicketActionEvent event= ticketWrapperImpl.TicketEvent("CREATED",ticket);
		kafkaTemplate.send("Ticket-Events","ProjectId:"+ticket.getProjectId(), event);
		
		return "Ticket created successfully";
	}
	
	//subjected for removal, as it is not used anywhere in the code., but just duplicating the functionality of getProjectTickets, but with a different name.
	public List<TicketResponseDto> getProjectTickets(int projectId)
	{
		// This method will return all the tickets of a project
		
		boolean ver=verifyEmployeeProjectId(projectId, Set.of("TEAM_LEAD","PROJECT_MANAGER"));
		if(!ver)
			throw new RuntimeException("UNAUTHORIZED");
		List<TicketResponseDto> tk= redisTemplate.opsForValue().get("projectTickets:"+projectId);
		
		if(tk==null)
		{
			List<Ticket> tickets = ticketRepo.findByProjectIdAndStatusAndAssignedToIsNull(projectId, TicketStatus.OPEN);
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
	
	//
	public String deleteTicket(int ticketId, long projectId)
	{
		// This method will delete a ticket
		boolean ver=verifyEmployeeProjectId(projectId, Set.of("TEAM_LEAD","PROJECT_MANAGER"));
		if(!ver)
			throw new RuntimeException("UNAUTHORIZED");
		
		Ticket ticket = ticketRepo.findById(ticketId).orElse(null);
		if(ticket != null && ticket.getProjectId() == projectId)
		{
			ticketRepo.deleteById(ticketId);
			
			redisTemplate.delete("projectTickets:"+projectId);
			redisTemplate.delete("AvailableTickets:"+ projectId);
			redisTemplate.delete("AllTickets:"+projectId);
			
			kafkaTemplate.send("Ticket-Events","ProjectId:"+projectId, new TicketActionEvent("DELETED", new TicketActionDto(ticket.getTitle(),ticket.getPriority(),ticket.getProjectId(),LocalDateTime.now())));
			return "Ticket deleted successfully";
		}
		else
		{
			return "Ticket not found";
		}
	}
	
	//
	public String updateTicketStatus(TicketStatusUpdateDto dto)
	{
		// This method will update the status of a ticket
		Ticket ticket = ticketRepo.findById(dto.getTicketId()).orElse(null);
		if(ticket==null || ticket.getProjectId()!=dto.getProjectId())
			throw new RuntimeException("Ticket not found");
		
		boolean ap=true;
		MemberDetailsDto dt= pfc.verifyEmployee(ticket.getProjectId()).getBody();
		if(dt==null || !Set.of("TEAM_LEAD","PROJECT_MANAGER", "DEVELOPER").contains(dt.getRole()))
			ap=false;
		
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!ap || !(ticket.getAssignedEmployeeId()==up.getEmployeeId()))
		{
			if(!Set.of("TEAM_LEAD", "PROJECT_MANAGER").contains(dt.getRole()))
				throw new RuntimeException("UNAUTHORIZED");		
		}
		
		Boolean upd=false;
		TicketStatus status=dto.getStatus();
		ticket.setStatus(status);
		ticket.setUpdatedAt(java.time.LocalDateTime.now());
			
		if(status == TicketStatus.RESOLVED)
		{
				upd=true;
				ticket.setClosedAt(java.time.LocalDateTime.now());
				ticket.setApproved(TicketApprovalStatus.PENDING);
		}
		else if(status == TicketStatus.REOPENED && Set.of("TEAM_LEAD", "PROJECT_MANAGER").contains(dt.getRole()))
		{
				
				upd=true;
				ticket.setAssignedTo(null);
				ticket.setClosedAt(null);
				redisTemplate.delete("userTickets:"+ticket.getAssignedEmployeeId());
				ticket.setAssignedEmployeeId(null);
		}
		else
		{
			throw new RuntimeException("INVALID/UNAUTHORIZED");
		}
			
		ticketRepo.save(ticket);
			
		redisTemplate.delete("projectTickets:"+ticket.getProjectId());
		redisTemplate.delete("AvailableTickets:"+ticket.getProjectId());
		redisTemplate.delete("AllTickets:"+ticket.getProjectId());
			
		if(upd)
		{
				kafkaTemplate.send("Ticket-Events","ProjectId:"+ticket.getProjectId(), new TicketActionEvent("UPDATED", new TicketActionDto(ticket.getTitle(),ticket.getPriority(),ticket.getProjectId(),LocalDateTime.now())));
		}
			
		return "Ticket status updated successfully";
	}
		
	//
	public String assignUserTicket(AssignUserDto dto) throws Exception
	{
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Ticket tk= ticketRepo.findById(dto.getTicketId()).orElseThrow(()-> new Exception("TICKET NOT FOUND !"));
		UserFeignDto ufr=  ufc.getUserFromAuthService(Long.valueOf(dto.getEmployeeId())).getBody();
		
		if(!tk.getStatus().equals(TicketStatus.REOPENED) || !tk.getStatus().equals(TicketStatus.OPEN))
		{
			throw new RuntimeException("Ticket Already Assigned");
		}
		if(ufr==null)
		{
			throw new RuntimeException("No User Found !");
		}
		
		MemberDetailsDto dt= pfc.verifyEmployee(tk.getProjectId()).getBody();
		
		boolean rp=false;
		if(ufr.getEmployeeId()!=up.getEmployeeId())
		{
			rp=true;
			if(Set.of("PROJECT_MANAGER", "TEAM_LEAD").contains(dt.getRole()))
			{
				boolean r= verifyEmployeeProjectId(tk.getProjectId(),dto.getEmployeeId(),Set.of("TEAM_LEAD","PROJECT_MANAGER", "DEVELOPER"));
				if(!r)
					throw new RuntimeException("UNAUTHORIZED");
			}
			else
			{
				throw new RuntimeException("UNAUTHORIZED");
			}
		}
		
		if(!rp)
		{
			boolean rs=true;
			if(dt==null || !Set.of("PROJECT_MANAGER", "TEAM_LEAD", "DEVELOPER").contains(dt.getRole()))
			{
				rs=false;
			}
			if(!rs)
				throw new RuntimeException("UNAUTHORIZED");
		}
		
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
	
	//
	public List<TicketResponseDto> getAvailableTicketsForUser(int projectId)
	{
		boolean rs= verifyEmployeeProjectId(projectId, Set.of("DEVELOPER"));
		if(!rs)
			throw new RuntimeException("UNAUTHORIZED");
		
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
	
	//
	@Transactional
	public String updateTicketApprovalStatus(TicketApprovalDto dto)
	{
		Ticket tk= ticketRepo.findByIdAndApproved(dto.getTicketId(), TicketApprovalStatus.PENDING).orElseThrow(()-> new RuntimeException("NO TICKET FOUND"));
		
		MemberDetailsDto userDetails= pfc.verifyEmployee(tk.getProjectId()).getBody();
		if(userDetails==null || !userDetails.getRole().equals("TEAM_LEAD"))
		{
			throw new RuntimeException("UNAUTHORIZED");
		}
		
		if(tk.getProjectId()!=dto.getProjectId())
		{
			throw new RuntimeException("No Ticket Found !");
		}
		
		if(dto.getStatus().equals(TicketApprovalStatus.PENDING) || !tk.getStatus().equals(TicketStatus.RESOLVED))
		{
			return "State Contradicts"; 
		}
		
		
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
		
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
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
	
	//
	public List<TicketResponseDto> getTicketBasedOnFilter(TicketStatus stat, TicketPriority prior, TicketApprovalStatus status, Long projectId)
	{
		Boolean rs= verifyEmployeeProjectId(projectId, Set.of("TEAM_LEAD","PROJECT_MANAGER"));
		if(!rs)
			throw new RuntimeException("UNAUTHORIZED");
		
		List<TicketResponseDto> res= ticketRepo.filterBySpecs(stat, prior, status, projectId)
											   .stream()
											   .map(r->{
												   return ticketWrapperImpl.toDto(r);
											   })
											   .toList();
		return res;
	}
	
	//
	public List<TicketResponseDto> getUserTickets()
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TicketResponseDto> dt=redisTemplate.opsForValue().get("userTickets:"+prin.getEmployeeId());
		
		if(dt==null)
		{
			List<TicketResponseDto> res= ticketRepo.findByAssignedEmployeeIdOrderByCreatedAtDesc(prin.getEmployeeId())
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
	//
	public List<TicketResponseDto> getAllTickets(int projectId)
	{	
			boolean ver= verifyEmployeeProjectId(projectId, Set.of("TEAM_LEAD","PROJECT_MANAGER"));
			if(!ver)
				throw new RuntimeException("UNAUTHORIZED");
			
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
	
	//
	public List<TicketResponseDto> getUnapprovedTickets(int projectid, TicketApprovalStatus status)
	{
			Boolean rs= verifyEmployeeProjectId(projectid, Set.of("TEAM_LEAD","PROJECT_MANAGER"));
			if(!rs)
				throw new RuntimeException("UNAUTHORIZED");
			
			List<TicketResponseDto> dto= ticketRepo.findByProjectIdAndApproved(projectid,status)
					  .stream()
					  .map(r->{
						  return ticketWrapperImpl.toDto(r);
					  })
					  .toList();
			return dto;
	}
	
	public Boolean verifyEmployeeProjectId(long projectId, Set<String> allowedRoles)
	{
		MemberDetailsDto dt= pfc.verifyEmployee(projectId).getBody();
		if(dt==null || !allowedRoles.contains(dt.getRole()) || !dt.getIsProjectActive())
		{
			return false;
		}
		return true;
	}
	
	public Boolean verifyEmployeeProjectId(long projectId, long employeeId, Set<String> allowedRoles)
	{
		MemberDetailsDto dt= pfc.verifyEmployee(projectId, employeeId).getBody();
		if(dt==null || !allowedRoles.contains(dt.getRole()))
		{
			return false;
		}
		return true;
	}
	
}
