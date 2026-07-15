package com.harish.tickit.notificationService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.tickit.notificationService.dtos.NotificationDto;
import com.harish.tickit.notificationService.services.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController 
{
	@Autowired
	private NotificationService ns;
	
	@PostMapping("/create")
	public ResponseEntity<String> createNotification(@RequestBody NotificationDto dto)
	{
		String res= ns.createNotification(dto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	
	
}
