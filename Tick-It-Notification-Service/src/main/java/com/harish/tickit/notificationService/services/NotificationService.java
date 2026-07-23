package com.harish.tickit.notificationService.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.tickit.notificationService.auth.UserPrincipal;
import com.harish.tickit.notificationService.dtos.NotificationResponseDto;
import com.harish.tickit.notificationService.dtos.NotificationUpdateDto;
import com.harish.tickit.notificationService.events.NotificationEvent;
import com.harish.tickit.notificationService.models.Notification;
import com.harish.tickit.notificationService.repos.NotificationRepo;
import jakarta.transaction.Transactional;

@Service
public class NotificationService
{
	@Autowired
	private NotificationRepo nr;
	
	public String createNotification(NotificationEvent ndto)
	{
		Notification nt= new Notification();
		nt.setCreatedAt(LocalDate.now());
		nt.setMessage(ndto.getMessage());
		nt.setRead(false);
		nt.setTitle(ndto.getTitle());
		nt.setType(ndto.getType());
		nt.setEmployeeId(ndto.getEmployeeId());
		
		nr.save(nt);
		
		return "Notification sent/saved";
	}
	
	@Transactional
	public String updateNotificationStatus(NotificationUpdateDto dto)
	{
		Notification nt= nr.findById(dto.getNotificationId()).orElseThrow(()-> new RuntimeException("Notification not found"));
		long userId= getEmployeeId();
		
		if(userId!=nt.getEmployeeId())
		{
			throw new RuntimeException("ACCESS DENIED");
		}
	
		nt.setRead(true);
		
		return "Notification updated !";
	}
	
	@Transactional
	public String updateNotificationStatusAll()
	{
		long userId= getEmployeeId();
		
		List<Notification> nt=nr.findByEmployeeIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId);
		nt.forEach(r->r.setRead(true));
		
		return "Updated";
								
	}
	
	public Integer getUnreadNotificationCount()
	{
		long userId= getEmployeeId();
		
		int count= nr.countByEmployeeIdAndReadFalseAndDeletedFalse(userId);
		return count;
	}
	
	public List<NotificationResponseDto> getAllUnreadNotifications()
	{
		
		long userId= getEmployeeId();
		List<NotificationResponseDto> res = nr.findByEmployeeIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId)
										     .stream()
										     .map(r->{
										    	 NotificationResponseDto dto= new NotificationResponseDto();
										    	 dto.setMessage(r.getMessage());
										    	 dto.setNotificationId(r.getId());
										    	 dto.setTitle(r.getTitle());
										    	 dto.setType(r.getType());
										    	 dto.setEmployeeId(r.getEmployeeId());
										    	 
										    	 return dto;
										     })
										     .toList();
		
		return res;
	}
	
	public List<NotificationResponseDto> getAllNotifications()
	{
		long userId= getEmployeeId();
		
		List<NotificationResponseDto> res = nr.findByEmployeeIdAndDeletedFalseOrderByCreatedAtDesc(userId)
			     							  .stream()
			     							  .map(r->{
			     								  NotificationResponseDto dto= new NotificationResponseDto();
			     								  dto.setMessage(r.getMessage());
			     								  dto.setNotificationId(r.getId());
			     								  dto.setTitle(r.getTitle());
			     								  dto.setType(r.getType());
			     								  dto.setEmployeeId(r.getEmployeeId());
			    	 
			     								  return dto;
			     							  })
			     							  .toList();
		return res;
	}
	
	@Transactional
	public String deleteNotifications(NotificationUpdateDto dto)
	{
		Notification nt= nr.findById(dto.getNotificationId()).orElseThrow(()-> new RuntimeException("Notification not found"));
		long userId= getEmployeeId();
		
		if(userId!=nt.getEmployeeId())
		{
			throw new RuntimeException("ACCESS DENIED");
		}
	
		nt.setDeleted(true);
		
		return "DELETED";
	}
	
	@Transactional
	public String deleteAllNotifications()
	{
		long userId= getEmployeeId();
		
		List<Notification> nt=nr.findByEmployeeIdAndDeletedFalseOrderByCreatedAtDesc(userId);
		nt.forEach(r->r.setDeleted(true));
		
		return "Deleted all";
	}
	
	public long getEmployeeId()
	{
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId=up.getEmployeeId();
		
		return userId;
	}
	
	
}
