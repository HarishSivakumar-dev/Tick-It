package com.harish.tickit.notificationService.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.harish.tickit.notificationService.events.NotificationEvent;
import com.harish.tickit.notificationService.services.NotificationService;

@Component
public class NotificationConsumer 
{
	@Autowired
	private NotificationService service;
	
	@KafkaListener(topics= "notification-topic")
	public void consume(NotificationEvent event)
	{
		service.createNotification(event);
	}
	
}
