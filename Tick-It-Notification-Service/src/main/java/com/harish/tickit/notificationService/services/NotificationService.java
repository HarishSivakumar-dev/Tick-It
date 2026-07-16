package com.harish.tickit.notificationService.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.harish.tickit.notificationService.dtos.NotificationDto;
import com.harish.tickit.notificationService.dtos.NotificationResponseDto;
import com.harish.tickit.notificationService.dtos.NotificationUpdateDto;
import com.harish.tickit.notificationService.models.Notification;
import com.harish.tickit.notificationService.repos.NotificationRepo;
import jakarta.transaction.Transactional;

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
	
	public String updateNotificationStatus(NotificationUpdateDto dto)
	{
		Notification nt= nr.findById(dto.getNotificationId()).orElseThrow(()-> new RuntimeException("Notification not found"));
		nt.setRead(true);
		nr.save(nt);
		
		return "Notification updated !";
	}
	
	@Transactional
	public String updateNotificationStatusAll(int userId)
	{
		List<Notification> nt=nr.findByUserIdAndReadFalse(userId);
		nt.forEach(r->r.setRead(true));
		
		return "Updated";
								
	}
	
	public Integer getUnreadNotificationCount(int userId)
	{
		int count= nr.countByUserIdAndReadFalse(userId);
		return count;
	}
	
	public List<NotificationResponseDto> getAllUnreadNotifications(int userId)
	{
		List<NotificationResponseDto> res = nr.findByUserIdAndReadFalse(userId)
										     .stream()
										     .map(r->{
										    	 NotificationResponseDto dto= new NotificationResponseDto();
										    	 dto.setMessage(r.getMessage());
										    	 dto.setNotificationId(r.getId());
										    	 dto.setTitle(r.getTitle());
										    	 dto.setType(r.getType());
										    	 dto.setUserId(r.getUserId());
										    	 
										    	 return dto;
										     })
										     .toList();
		
		return res;
	}
	
	
	
	

}
