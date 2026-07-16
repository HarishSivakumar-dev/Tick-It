package com.harish.tickit.notificationService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.tickit.notificationService.models.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer>
{
	int countByUserIdAndReadFalse(int userid);
	List<Notification> findByUserIdAndReadFalse(int userId);

}
