package com.harish.TickIt.ProjectService.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.TickIt.ProjectService.models.ProjectBelongers;

public interface ProjectMembershipRepo extends JpaRepository<ProjectBelongers, Integer>
{
	List<ProjectBelongers> findByProjectId(long id);
	List<ProjectBelongers> findByEmployeeId(long id);
}
