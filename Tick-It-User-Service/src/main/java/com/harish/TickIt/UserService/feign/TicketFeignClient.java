package com.harish.TickIt.UserService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.harish.TickIt.UserService.dtos.TicketDetailsDto;
import com.harish.TickIt.UserService.dtos.TicketResponseDto;
import com.harish.TickIt.UserService.dtos.TicketUpdateDto;

@FeignClient(name = "Tick-It-Ticket-Service")
public interface TicketFeignClient
{
	@PostMapping("/api/tickets/create")
	public ResponseEntity<String> ticketCreation(@RequestBody TicketDetailsDto dto);
	
	@GetMapping("/api/tickets/project/{projectId}")
	public ResponseEntity<List<TicketResponseDto>> getProjectTickets(@PathVariable int projectId);
	
	@DeleteMapping("/api/tickets/delete/{ticketId}")
	public ResponseEntity<String> deleteTicket(@PathVariable int ticketId);
	
	@PostMapping("/api/tickets/update")
	public ResponseEntity<String> updateTicket(@RequestBody TicketUpdateDto dto);
}
