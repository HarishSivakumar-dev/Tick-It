package com.harish.tickit.notificationService.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.tickit.notificationService.dtos.NotificationDto;
import com.harish.tickit.notificationService.dtos.NotificationResponseDto;
import com.harish.tickit.notificationService.dtos.NotificationUpdateDto;
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
	
	@PatchMapping("/update")
	public ResponseEntity<String> updateNotificationStatus(@RequestBody NotificationUpdateDto dto)
	{
		String res= ns.updateNotificationStatus(dto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@PatchMapping("/update/all")
	public ResponseEntity<String> updateNotificationStatusAll()
	{
		String res= ns.updateNotificationStatusAll();
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/get/unread/count")
	public ResponseEntity<Integer> getUnreadNotificationCount()
	{
		Integer it= ns.getUnreadNotificationCount();
		return ResponseEntity.status(HttpStatus.OK).body(it);
	}
	
	@GetMapping("/get/unread/all")
	public ResponseEntity<List<NotificationResponseDto>> getUnreadNotificationAll()
	{
		List<NotificationResponseDto> dt= ns.getAllUnreadNotifications();
		return ResponseEntity.status(HttpStatus.OK).body(dt);
	}
	
	@GetMapping("/get/all")
	public ResponseEntity<List<NotificationResponseDto>> getAllNotifications()
	{
		List<NotificationResponseDto> dt= ns.getAllNotifications();
		return ResponseEntity.status(HttpStatus.OK).body(dt);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteNotification(@RequestBody NotificationUpdateDto dto)
	{
		String res= ns.deleteNotifications(dto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<String> deleteAllNotifications()
	{
		String res= ns.deleteAllNotifications();
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
		
}
