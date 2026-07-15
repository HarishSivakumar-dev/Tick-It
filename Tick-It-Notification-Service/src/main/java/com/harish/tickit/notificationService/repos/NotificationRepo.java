package com.harish.tickit.notificationService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.tickit.notificationService.models.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer>
{

}
