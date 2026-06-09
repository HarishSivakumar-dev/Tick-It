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
import com.harish.TickIt.UserService.feign.TicketFeignClient;

@RestController
@RequestMapping("/api/v1/user-action")
public class UserActionController
{
	@Autowired
	private TicketFeignClient ticketFeignClient;
	
	
	@PostMapping("/create-ticket")
	public ResponseEntity<String> createTicket(@RequestBody com.harish.TickIt.UserService.dtos.TicketDetailsDto dto)
	{
		ResponseEntity<String> st=ticketFeignClient.ticketCreation(dto);
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(st.getBody());
	}
	
	@GetMapping("/project-tickets/{projectId}")
	public ResponseEntity<List<com.harish.TickIt.UserService.dtos.TicketResponseDto>> getProjectTickets(@PathVariable int projectId)
	{	
		ResponseEntity<List<TicketResponseDto>> st=ticketFeignClient.getProjectTickets(projectId);
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(st.getBody());
	}
	
	@DeleteMapping("/delete-ticket/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable int ticketId)
	{
		ResponseEntity<String> st=ticketFeignClient.deleteTicket(ticketId);
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(st.getBody());
	}

}
