package com.harish.TickIt.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.harish.TickIt.models.SessionManagement;

@Repository
public interface SessionManagementRepo extends JpaRepository<SessionManagement, UUID>
{

	Optional<SessionManagement> findByRefreshTokenJti(UUID jti);
	
	void deleteByRefreshTokenJti(UUID jti);
	
	Optional<SessionManagement> findBySessionId(UUID sessionId);
	
	@Modifying
	@Transactional
	int deleteByExpiresAtBeforeOrRevokedTrue(java.time.LocalDateTime expirationTime);
	
	List<SessionManagement> findByEmployeeID(Long employeeID);
	
}
