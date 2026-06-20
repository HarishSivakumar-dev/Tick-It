package com.harish.TickIt.ProjectService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.TickIt.ProjectService.models.ProjectDetails;

import java.util.Optional;

@Repository
public interface ProjectDetailsRepo extends JpaRepository<ProjectDetails, Integer>
{
	Optional<ProjectDetails> findByProjectId(Long projectname);

}
