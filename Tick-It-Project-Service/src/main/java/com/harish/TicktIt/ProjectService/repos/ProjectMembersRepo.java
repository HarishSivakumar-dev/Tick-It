package com.harish.TicktIt.ProjectService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.TicktIt.ProjectService.models.ProjectMembers;

public interface ProjectMembersRepo extends JpaRepository<ProjectMembers, Integer>
{
	List<ProjectMembers> findByProjectIdIsNull();

}
