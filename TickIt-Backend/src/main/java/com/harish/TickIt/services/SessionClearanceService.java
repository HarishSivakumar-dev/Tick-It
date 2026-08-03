package com.harish.TickIt.services;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.harish.TickIt.models.SessionManagement;
import com.harish.TickIt.repositories.SessionManagementRepo;

@Component
public class SessionClearanceService 
{
	@Autowired
	private SessionManagementRepo smr;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void clearSession(UUID uuid) 
	{
		Optional<SessionManagement> sm= smr.findByRefreshTokenJti(uuid);
		if(sm.isPresent())
		{
			sm.get().setRevoked(Boolean.TRUE);
		}
		else
		{
			throw new RuntimeException("Session not found for the provided refresh token.");
		}
	}

}
