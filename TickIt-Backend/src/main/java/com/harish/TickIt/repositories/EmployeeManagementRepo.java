package com.harish.TickIt.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeManagementRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.models.EmployeeManagement, Long>
{
	
}
