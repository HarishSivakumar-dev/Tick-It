package com.harish.TickIt.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.models.SessionManagement;

@Repository
public interface SessionManagementRepo extends JpaRepository<SessionManagement, UUID>
{

	Optional<SessionManagement> findByRefreshTokenJti(UUID jti);
	
	void deleteByRefreshTokenJti(UUID jti);
	
}
