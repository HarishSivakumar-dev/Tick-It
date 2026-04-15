package com.harish.TickIt.UserService.controllers;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-action")
public class UserActionController
{
	public ResponseEntity<String> createTicket()
	{
		return ResponseEntity.status(HttpStatus.SC_OK).body("Ticket Created");
	}
	

}
