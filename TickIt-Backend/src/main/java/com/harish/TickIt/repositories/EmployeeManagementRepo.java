package com.harish.TickIt.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.models.EmployeeManagement;

@Repository
public interface EmployeeManagementRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.models.EmployeeManagement, Long>
{
	Optional<com.harish.TickIt.models.EmployeeManagement> findByEmail(String email);
	Optional<EmployeeManagement> findByEmailAndId(String email, Long id);
}
