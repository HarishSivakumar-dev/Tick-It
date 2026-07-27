package com.harish.TickIt.TicketService.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.TicketService.dtos.TicketCommentDto;
import com.harish.TickIt.TicketService.dtos.TicketCommentResponseDto;
import com.harish.TickIt.TicketService.services.TicketCommentService;

@RestController
@RequestMapping("/api/tickets")
public class TicketCommentController
{
	@Autowired
	private TicketCommentService tcs;
	
	@PostMapping("/comment/{ticketId}/create")
	public ResponseEntity<String> createComment(@RequestBody TicketCommentDto dto, @PathVariable int ticketId)
	{
		String res= tcs.createComment(ticketId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/comment/get/{ticketId}")
	public ResponseEntity<List<TicketCommentResponseDto>> getAllCommentsForTicket(@PathVariable int ticketId)
	{
		List<TicketCommentResponseDto> ls= new ArrayList<>();
		return ResponseEntity.status(HttpStatus.OK).body(ls);
	}
	
	
}
