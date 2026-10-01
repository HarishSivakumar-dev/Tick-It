package com.harish.tickit.notificationService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.tickit.notificationService.models.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer>
{
	int countByEmployeeIdAndIsReadFalseAndDeletedFalse(long employeeid);
	List<Notification> findByEmployeeIdAndIsReadFalseAndDeletedFalseOrderByCreatedAtDesc(long employeeid);
	List<Notification> findByEmployeeIdAndDeletedFalseOrderByCreatedAtDesc(long employeeid);

}
