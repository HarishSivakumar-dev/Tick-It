package com.harish.TicktIt.ProjectService.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.harish.TickIt.ProjectService.dtos.MemberDetailsDto;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;
import com.harish.TicktIt.ProjectService.dtos.UserProjectDetailsDto;
import com.harish.TicktIt.ProjectService.dtos.UserProjectDto;
import com.harish.TicktIt.ProjectService.models.ProjectDetails;
import com.harish.TicktIt.ProjectService.models.ProjectMembers;
import com.harish.TicktIt.ProjectService.repos.ProjectDetailsRepo;
import com.harish.TicktIt.ProjectService.repos.ProjectMembersRepo;

public class ProjectService
{
	@Autowired
	private ProjectDetailsRepo pdr;
	@Autowired
	private ProjectMembersRepo pmr;
	
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
	
	public List<UserDetailsDto> getAllEmployeesUnassigned()
	{
		List<UserDetailsDto> res= pmr.findByProjectIdIsNull().stream()
															 .map(r->{
																 UserDetailsDto dto= new UserDetailsDto();
																 dto.setMailId(r.getMailId());
																 dto.setProjectId(r.getProjectId());
																 dto.setUserId(r.getUserId());
																 dto.setUserName(r.getUserName());
																 
																 return dto;
															 })
															 .toList();
		
		return res;
	}
	
	public String userInfoPopulation(UserProjectDto dto)
	{
		ProjectMembers pm= new ProjectMembers();
		pm.setUserId(dto.getUserId());
		pm.setUserName(dto.getUserName());
		pm.setProjectId(null);
		pm.setMailId(dto.getEmail());
		pm.setAssignedDate(null);
		pm.setRole(null);
		pm.setRelievedDate(null);
		
		pmr.save(pm);
		
		return "Saved";
	}
	
	public List<UserProjectDetailsDto> getAllUserProjects()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		List<ProjectMembers> proj= pmr.findByUserNameAndProjectIdNotNull(name);
		
		if(proj.isEmpty())
		{
			List<UserProjectDetailsDto> dt= null;
			return dt;
		}
		else
		{
			List<UserProjectDetailsDto> res= proj.stream()
					 .map(r->{
						 UserProjectDetailsDto dto= new UserProjectDetailsDto();
						 dto.setAssignedDate(r.getAssignedDate());
						 dto.setMailId(r.getMailId());
						 dto.setProjectId(r.getProjectId());
						 dto.setRelievedDate(r.getRelievedDate());
						 dto.setRole(r.getRole());
						 dto.setUserId(r.getUserId());
						 dto.setUserName(r.getUserName());
						 
						 return dto;
					 })
					 .toList();
			return res;
		}
	}
	
	public String addMembersIntoProject(List<MemberDetailsDto> dto)
	{
		List<ProjectMembers> ls= dto.stream()
									.map(r->{
										ProjectMembers pm= new ProjectMembers();
										pm.setAssignedDate(LocalDate.now());
										pm.setMailId(r.getMailId());
										pm.setProjectId(r.getProjectId());
										pm.setRelievedDate(null);
										pm.setRole(r.getRole());
										pm.setUserId(r.getUserId());
										pm.setUserName(r.getUserName());
										
										return pm;
									})
									.toList();
		pmr.saveAll(ls);
		
		return "Saved all Details";
	}
}
