package com.harish.TickIt.TicketService.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.TicketService.dtos.AssignUserDto;
import com.harish.TickIt.TicketService.dtos.TicketApprovalDto;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.dtos.TicketStatusUpdateDto;
import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.services.TicketActionService;

@RestController
@RequestMapping("/api/tickets")
public class TicketActionController
{
	@Autowired
	private TicketActionService tcs;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('LEAD')")
	public ResponseEntity<String> createTicket(@RequestBody TicketDetailsDto dto)
	{
		String response = tcs.createTicket(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/project/{projectId}")
	@PreAuthorize("hasRole('LEAD')")
	public ResponseEntity<List<TicketResponseDto>> getProjectTickets(@PathVariable int projectId)
	{
		List<TicketResponseDto> response = tcs.getProjectTickets(projectId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/delete/{ticketId}")
	@PreAuthorize("hasRole('LEAD')")
	public ResponseEntity<String> deleteTicket(@PathVariable int ticketId)
	{
		String response = tcs.deleteTicket(ticketId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/assign/user/ticket")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> assignUserTicket(@RequestBody AssignUserDto dtos) throws Exception
	{
		String response= tcs.assignUserTicket(dtos);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/tickets/open/get/{projectId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<TicketResponseDto>> getTicketsForUser(@PathVariable int projectId)
	{
		List<TicketResponseDto> response= tcs.getAvailableTicketsForUser(projectId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/ticket/update/approval/status")
	@PreAuthorize("hasRole('LEAD')")
	public ResponseEntity<String> updateTicketApproval(@RequestBody TicketApprovalDto dto)
	{
		String str= tcs.updateTicketApprovalStatus(dto);
		return ResponseEntity.status(HttpStatus.OK).body(str);
	}
	
	@GetMapping("/get/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<TicketResponseDto>> getFilteredTickets(@RequestParam(required=false) TicketStatus status,
																	  @RequestParam(required=false) TicketPriority priority, 
																	  @RequestParam(required=false) TicketApprovalStatus appStat)
	{
		List<TicketResponseDto> res= tcs.getTicketBasedOnFilter(status, priority, appStat);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@PostMapping("/update/status")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateTicketStatus(@RequestBody TicketStatusUpdateDto dto)
	{
		String res= tcs.updateTicketStatus(dto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
}
