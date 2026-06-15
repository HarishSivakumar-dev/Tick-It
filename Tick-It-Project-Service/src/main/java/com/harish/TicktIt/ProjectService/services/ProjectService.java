package com.harish.TicktIt.ProjectService.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TicktIt.ProjectService.repos.ProjectDetailsRepo;

public class ProjectService
{
	@Autowired
	private ProjectDetailsRepo pdr;
	
	public List<ProjectResponseDto> getAllProjects()
	{
		List<ProjectResponseDto> pd= pdr.findAll().stream()
												  .map(r-> {
													  ProjectResponseDto dto= new ProjectResponseDto();
													  dto.setActive(r.isActive());
													  dto.setCreatedAt(r.getCreatedAt());
													  dto.setEndDate(r.getEndDate());
													  dto.setProjectDescription(r.getProjectDescription());
													  dto.setProjectId(r.getProjectId());
													  dto.setProjectManagerId(r.getProjectManagerId());
													  dto.setProjectName(r.getProjectName());
													  dto.setStartDate(r.getStartDate());
													  dto.setStatus(r.getStatus());
													  dto.setUpdatedAt(r.getUpdatedAt());
													  
													  return dto;
												  })
												  .toList();
		return pd;
		
	}
}
