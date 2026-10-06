package com.harish.tickit.notificationService.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import com.harish.tickit.notificationService.events.NotificationEvent;
import com.harish.tickit.notificationService.events.TicketCreationEvent;
import com.harish.tickit.notificationService.services.NotificationService;

@Component
public class NotificationConsumer 
{
	@Autowired
	private NotificationService service;
	
	@KafkaListener(
			topics =  "Ticket-Events",
			groupId = "tickit-debug"
			)
	public void consume(TicketCreationEvent event)
	{
		service.createNotification(event);
	}
	
}
