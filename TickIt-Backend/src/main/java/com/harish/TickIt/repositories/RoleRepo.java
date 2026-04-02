package com.harish.TickIt.repositories;

import java.util.Optional;
import com.harish.TickIt.models.Roles;

public interface RoleRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.models.Roles, Long>
{
	Optional<Roles> findByRoleName(String roleName);
}
