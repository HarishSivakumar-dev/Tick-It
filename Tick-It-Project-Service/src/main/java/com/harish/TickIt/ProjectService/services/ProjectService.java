package com.harish.TickIt.ProjectService.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.dtos.MemberDetailsDto;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
import com.harish.TickIt.ProjectService.dtos.ProjectDetDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDetailsDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDto;
import com.harish.TickIt.ProjectService.enums.ProjectStatus;
import com.harish.TickIt.ProjectService.feign.UserInformationFeign;
import com.harish.TickIt.ProjectService.models.ProjectDetails;
import com.harish.TickIt.ProjectService.models.ProjectMembers;
import com.harish.TickIt.ProjectService.repos.ProjectDetailsRepo;
import com.harish.TickIt.ProjectService.repos.ProjectMembersRepo;

import jakarta.transaction.Transactional;

@Component
public class ProjectService
{
	@Autowired
	private ProjectDetailsRepo pdr;
	@Autowired
	private ProjectMembersRepo pmr;
	@Autowired
	private UserInformationFeign uif;
	
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
	
	@Transactional
	public String projectCreation(ProjectCreationDto dto)
	{
		ProjectDetails pd= new ProjectDetails();
		
		pd.setActive(dto.isActive());
		pd.setCreatedAt(LocalDateTime.now());
		pd.setEndDate(null);
		pd.setProjectDescription(dto.getProjectDescription());
		
		if(uif.findProjectManagerId(dto.getProjectManagerId()).getBody().equals(Boolean.TRUE))
		{
			pd.setProjectManagerId(dto.getProjectManagerId());
		}
		else
		{
			throw new RuntimeException("NO PROJECT MANAGER FOUND WITH THAT ID");
		}
		
		pd.setProjectName(dto.getProjectName());
		pd.setStartDate(dto.getStartDate());
		pd.setStatus(dto.getStatus());
		pd.setUpdatedAt(null);
		
		pdr.save(pd);
		
		return "Record Saved";
		
	}
	
	@Transactional
	public String projectDetailsUpdation(ProjectCreationDto dto)
	{
		ProjectDetails det= pdr.findByProjectId(dto.getProjectId()).orElseThrow(()-> new RuntimeException("No Project Id found"));
		det.setUpdatedAt(LocalDateTime.now());
		
		if(dto.getEndDate()!=null)
			det.setEndDate(dto.getEndDate());
		if(dto.getProjectDescription()!=null)
			det.setProjectDescription(dto.getProjectDescription());
		if(dto.getStatus()!=null)
		{
			det.setStatus(dto.getStatus());
			if(dto.getStatus()==ProjectStatus.CANCELLED || dto.getStatus()==ProjectStatus.COMPLETED)
			{
				List<ProjectMembers> ls= pmr.findByProjectId(det.getProjectId())
											.stream()
											.map(r->{
												r.setProjectId(null);
												r.setRole(null);
												r.setRelievedDate(LocalDate.now());
												return r;
											})
											.toList();
				pmr.saveAll(ls);
			}
		}
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
	
	@Transactional
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
	
	@Transactional
	public String addMembersIntoProject(List<MemberDetailsDto> dto)
	{
		List<Long> ls= dto.stream()
						  .map(r->{
								return r.getUserId();
						   })
						  .toList();
		
		List<ProjectMembers> pm= pmr.findByUserIdIn(ls);
		Map<Long, MemberDetailsDto> mp= new HashMap<Long, MemberDetailsDto>();
		
		for(MemberDetailsDto dt : dto)
		{
			mp.put(dt.getUserId(), dt);
		}
		
		for(ProjectMembers p : pm)
		{
			p.setAssignedDate(LocalDate.now());
			p.setRole(mp.get(p.getUserId()).getRole());
			p.setProjectId(mp.get(p.getUserId()).getProjectId());
			p.setRelievedDate(null);
		}
		
		pmr.saveAll(pm);
		
		return "Saved all Details";
	}
	
	public List<UserProjectDetailsDto> getProjectMembers(Long projectid)
	{
		List<UserProjectDetailsDto> ls= pmr.findByProjectId(projectid)
				                           .stream()
				                           .map(r->
				                           {
				                        	   UserProjectDetailsDto dt= new UserProjectDetailsDto();
				                        	   dt.setAssignedDate(r.getAssignedDate());
				                        	   dt.setMailId(r.getMailId());
				                        	   dt.setProjectId(r.getProjectId());
				                        	   dt.setRelievedDate(r.getRelievedDate());
				                        	   dt.setRole(r.getRole());
				                        	   dt.setUserId(r.getUserId());
				                        	   dt.setUserName(r.getUserName());
				                        	   
				                        	   return dt;
				                           })
				                           .toList();
		return ls;
		
	}

	public Optional<ProjectDetDto> findProject(long projectid) 
	{
		Optional<ProjectDetails> pm= pdr.findByProjectId(projectid);
		
		if(pm.isEmpty())
		{
			return Optional.of(null);
		}
		else
		{
			ProjectDetails pd= pm.get();
			
			ProjectDetDto pdt= new ProjectDetDto();
			pdt.setActive(pd.isActive());
			pdt.setProjectId(pd.getProjectId());
			pdt.setProjectName(pd.getProjectName());
			
			return Optional.of(pdt);
		}	
	}
	

}
