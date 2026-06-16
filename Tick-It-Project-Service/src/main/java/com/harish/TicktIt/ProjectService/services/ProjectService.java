package com.harish.TicktIt.ProjectService.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TicktIt.ProjectService.models.ProjectDetails;
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
	
	public String projectCreation(ProjectCreationDto dto)
	{
		ProjectDetails pd= new ProjectDetails();
		
		pd.setActive(dto.isActive());
		pd.setCreatedAt(LocalDateTime.now());
		pd.setEndDate(null);
		pd.setProjectDescription(dto.getProjectDescription());
		pd.setProjectManagerId(dto.getProjectManagerId());
		pd.setProjectName(dto.getProjectName());
		pd.setStartDate(dto.getStartDate());
		pd.setStatus(dto.getStatus());
		pd.setUpdatedAt(null);
		
		pdr.save(pd);
		
		return "Record Saved";
		
	}
	
	public String projectDetailsUpdation(ProjectCreationDto dto)
	{
		ProjectDetails det= pdr.findByProjectName(dto.getProjectName()).orElseThrow();
		det.setUpdatedAt(LocalDateTime.now());
		
		if(dto.getEndDate()!=null)
			det.setEndDate(dto.getEndDate());
		if(dto.getProjectDescription()!=null)
			det.setProjectDescription(dto.getProjectDescription());
		if(dto.getStatus()!=null)
			det.setStatus(dto.getStatus());
		if(dto.isActive() != null)
			det.setActive(dto.isActive());
		
		pdr.save(det);
		
		return "Updated Successfully";
		
	}
}
