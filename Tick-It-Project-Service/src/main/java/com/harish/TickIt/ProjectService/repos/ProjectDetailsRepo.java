package com.harish.TicktIt.ProjectService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.TicktIt.ProjectService.models.ProjectDetails;
import java.util.Optional;

public interface ProjectDetailsRepo extends JpaRepository<ProjectDetails, Integer>
{
	Optional<ProjectDetails> findByProjectId(Long projectname);

}
