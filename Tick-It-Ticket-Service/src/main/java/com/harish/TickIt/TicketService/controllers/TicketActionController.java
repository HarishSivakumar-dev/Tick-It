package com.harish.TickIt.TicketService.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.TicketService.dtos.AssignUserDto;
import com.harish.TickIt.TicketService.dtos.TicketDetailsDto;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import com.harish.TickIt.TicketService.services.TicketActionService;

@RestController
@RequestMapping("/api/tickets")
public class TicketActionController
{
	@Autowired
	private TicketActionService tcs;
	
	@PostMapping("/create")
	public ResponseEntity<String> createTicket(@RequestBody TicketDetailsDto dto)
	{
		String response = tcs.createTicket(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<TicketResponseDto>> getProjectTickets(@PathVariable int projectId)
	{
		List<TicketResponseDto> response = tcs.getProjectTickets(projectId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/delete/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable int ticketId)
	{
		String response = tcs.deleteTicket(ticketId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/assign/user/ticket")
	public ResponseEntity<String> assignUserTicket(@RequestBody AssignUserDto dtos) throws Exception
	{
		String response= tcs.assignUserTicket(dtos);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/tickets/open/get/{projectId}")
	public ResponseEntity<List<TicketResponseDto>> getTicketsForUser(@PathVariable int projectId)
	{
		List<TicketResponseDto> response= tcs.getAvailableTicketsForUser(projectId);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
