package com.harish.TickIt.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.harish.TickIt.repositories.SessionManagementRepo;

@Component
public class ExpiredSessionClearanceScheduler
{
	@Autowired
	private SessionManagementRepo sessionManagementRepo;
	
	@Scheduled(cron = "0 0 * * * ?") // Runs every hour
	public void clearExpiredSessions()
	{
		int rows= sessionManagementRepo.deleteByExpiresAtBeforeOrRevokedTrue(java.time.LocalDateTime.now());
		
		if(rows>0)
		{
			System.out.println("Expired sessions cleared: " + rows);
		}
	}

}
