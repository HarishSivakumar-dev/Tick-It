package com.harish.TickIt.ProjectService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.ProjectService.models.ProjectMembers;

@Repository
public interface ProjectMembersRepo extends JpaRepository<ProjectMembers, Integer>
{
	List<ProjectMembers> findByProjectIdIsNull();
	List<ProjectMembers> findByEmployeeIdAndProjectIdNotNull(Long id);
	List<ProjectMembers> findByProjectId(Long id);
	List<ProjectMembers> findByEmployeeIdIn(List<Long> ls);
	Boolean existsByEmployeeIdAndProjectId(Long employeeId, long projectId);
}
