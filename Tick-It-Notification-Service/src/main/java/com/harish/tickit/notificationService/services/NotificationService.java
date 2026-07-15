package com.harish.tickit.notificationService.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.harish.tickit.notificationService.dtos.NotificationDto;
import com.harish.tickit.notificationService.models.Notification;
import com.harish.tickit.notificationService.repos.NotificationRepo;

@Service
public class NotificationService
{
	@Autowired
	private NotificationRepo nr;
	
	public String createNotification(NotificationDto ndto)
	{
		Notification nt= new Notification();
		nt.setCreatedAt(LocalDate.now());
		nt.setMessage(ndto.getMessage());
		nt.setRead(false);
		nt.setTitle(ndto.getTitle());
		nt.setType(ndto.getType());
		nt.setUserId(ndto.getUserId());
		
		nr.save(nt);
		
		return "Notification sent/saved";
	}

}
