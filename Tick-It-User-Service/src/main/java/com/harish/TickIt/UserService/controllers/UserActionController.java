package com.harish.TickIt.UserService.controllers;

import java.util.List;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.UserService.dtos.TicketResponseDto;
import com.harish.TickIt.UserService.dtos.TicketUpdateDto;
import com.harish.TickIt.UserService.services.UserService;

@RestController
@RequestMapping("/api/v1/user-action")
public class UserActionController
{	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create-ticket")
	public ResponseEntity<String> createTicket(@RequestBody com.harish.TickIt.UserService.dtos.TicketDetailsDto dto)
	{
		String st=userService.createTicket(dto);
		return ResponseEntity.status(HttpStatus.SC_OK).body(st);
	}
	
	@GetMapping("/project-tickets/{projectId}")
	public ResponseEntity<List<com.harish.TickIt.UserService.dtos.TicketResponseDto>> getProjectTickets(@PathVariable int projectId)
	{	
		List<TicketResponseDto> st=userService.getProjectTickets(projectId);
		return ResponseEntity.status(HttpStatus.SC_OK).body(st);
	}
	
	@DeleteMapping("/delete-ticket/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable int ticketId)
	{
		String st=userService.deleteTicket(ticketId);
		return ResponseEntity.status(HttpStatus.SC_OK).body(st);
	}
	
	@PostMapping("/update-ticket")
	public ResponseEntity<String> updateTicket(@RequestBody TicketUpdateDto dto)
	{
		String st=userService.updateTicket(dto);
		return ResponseEntity.status(HttpStatus.SC_OK).body(st);
	}

}
