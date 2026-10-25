package com.harish.TickIt.ProjectService.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.auth.UserPrincipal;
import com.harish.TickIt.ProjectService.dtos.MemberDetailsDto;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
import com.harish.TickIt.ProjectService.dtos.ProjectDetDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDetailsDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDto;
import com.harish.TickIt.ProjectService.enums.ProjectStatus;
import com.harish.TickIt.ProjectService.feign.UserInformationFeign;
import com.harish.TickIt.ProjectService.models.ProjectBelongers;
import com.harish.TickIt.ProjectService.models.ProjectDetails;
import com.harish.TickIt.ProjectService.models.ProjectMembers;
import com.harish.TickIt.ProjectService.repos.ProjectDetailsRepo;
import com.harish.TickIt.ProjectService.repos.ProjectMembersRepo;
import com.harish.TickIt.ProjectService.repos.ProjectMembershipRepo;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	@Autowired
	private CacheManager manager;
	@Autowired
	private ProjectMembershipRepo rep;
	
	//
	@Cacheable(value="AllProjects")
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
	
	//
	@Transactional
	@CacheEvict(value="AllProjects")
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
	@Caching(evict= {@CacheEvict(value="AllProjects"),
					 @CacheEvict(value="ProjectId", key="#dto.projectId")})
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
				List<ProjectBelongers> ls= rep.findByProjectId(det.getProjectId())
											.stream()
											.map(r->{
												r.setProjectId(null);
												r.setRole(null);
												r.setRelievedDate(LocalDateTime.now());
												return r;
											})
											.toList();
				rep.saveAll(ls);
				
				for(ProjectBelongers m : ls)
				{
					manager.getCache("UserProjects").evict(m.getEmployeeId());
				}
				
				manager.getCache("ProjectMembersUnassigned").clear();
				manager.getCache("ProjectMembers").evict(det.getProjectId());
			}
		}
		if(dto.isActive() != null)
			det.setActive(dto.isActive());
		
		pdr.save(det);
		
		return "Updated Successfully";
		
	}
	
	@Cacheable(value="ProjectMembersUnassigned")
	public List<UserDetailsDto> getAllEmployeesUnassigned()
	{
		List<UserDetailsDto> res= pmr.findAll().stream()
											   .map(r->{
															UserDetailsDto dto= new UserDetailsDto();
															dto.setMailId(r.getMailId());
															dto.setEmployeeId(r.getEmployeeId());
															dto.setUserName(r.getUserName());
																 
															return dto;
														})
											   .toList();
		
		return res;
	}
	
	@Transactional
	@CacheEvict(value="ProjectMembersUnassigned")
	public String userInfoPopulation(UserProjectDto dto)
	{
		ProjectMembers pm= new ProjectMembers();
		pm.setEmployeeId(dto.getEmployeeId());
		pm.setUserName(dto.getUserName());
		pm.setMailId(dto.getEmail());
		
		pmr.save(pm);
		
		return "Saved";
	}
	
	@Cacheable(value="UserProjects", key="#employeeId")
	public List<UserProjectDetailsDto> getAllUserProjects(long employeeId)
	{
		List<ProjectBelongers> proj= rep.findByEmployeeId(employeeId);
								
		
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
						 dto.setEmployeeId(r.getEmployeeId());
						 dto.setUserName(r.getUserName());
						 
						 return dto;
					 })
					 .toList();
			return res;
		}
	}
	
	@Transactional
	@Caching(evict= {@CacheEvict(value="ProjectMembersUnassigned"),
					})	
	public String addMembersIntoProject(List<MemberDetailsDto> dto)
	{
		
		List<String> lt=dto.stream()
						   .map(r->{
							   String role=r.getRole().toString();
							   return role;
						   })
						   .toList();
		
		if(!lt.contains("TEAM_LEAD") && !lt.contains("PROJECT_MANAGER"))
		{
			return "Should contain atleast 1 teamlead/project_manager"; 
		}
		
		List<Long> ls= dto.stream()
						  .map(r->{
								return r.getEmployeeId();
						   })
						  .toList();
		
		List<ProjectMembers> bel= pmr.findByEmployeeIdIn(ls);
		
		if(bel.size()!=dto.size())
		{
			throw new RuntimeException("SOME EMPLOYEES NOT FOUND");
		}
		
		List<ProjectBelongers> bl= new ArrayList<ProjectBelongers>();
		for(MemberDetailsDto dt : dto)
		{
			ProjectBelongers b= new ProjectBelongers();
			b.setActive(true);
			b.setAssignedDate(LocalDateTime.now());
			b.setEmployeeId(dt.getEmployeeId());
			b.setProjectId(dt.getProjectId());
			b.setRole(dt.getRole());
			b.setUserName(dt.getUserName());
			b.setMailId(dt.getMailId());
			
			bl.add(b);
			
			manager.getCache("UserProjects").evict(dt.getEmployeeId());
			manager.getCache("ProjectMembers").evict(dt.getProjectId());
		}
		
		rep.saveAll(bl);
		
		return "Saved all Details";
	}
	
	@Cacheable(value="ProjectMembers", key="#projectId")
	public List<UserProjectDetailsDto> getProjectMembers(Long projectId)
	{
		List<UserProjectDetailsDto> ls= rep.findByProjectId(projectId)
				                           .stream()
				                           .map(r->
				                           {
				                        	   UserProjectDetailsDto dt= new UserProjectDetailsDto();
				                        	   dt.setAssignedDate(r.getAssignedDate());
				                        	   dt.setMailId(r.getMailId());
				                        	   dt.setProjectId(r.getProjectId());
				                        	   dt.setRelievedDate(r.getRelievedDate());
				                        	   dt.setRole(r.getRole());
				                        	   dt.setEmployeeId(r.getEmployeeId());
				                        	   dt.setUserName(r.getUserName());
				                        	   
				                        	   return dt;
				                           })
				                           .toList();
		return ls;
		
	}

	@Cacheable(value="ProjectId", key="#projectId")
	public ProjectDetDto findProject(long projectId) 
	{
		Optional<ProjectDetails> pm= pdr.findByProjectId(projectId);
		
		if(pm.isEmpty())
		{
			throw new RuntimeException("NO PROJECT FOUND !");
		}
		else
		{
			ProjectDetails pd= pm.get();
			
			ProjectDetDto pdt= new ProjectDetDto();
			pdt.setActive(pd.isActive());
			pdt.setProjectId(pd.getProjectId());
			pdt.setProjectName(pd.getProjectName());
			
			return pdt;
		}	
	}

	public MemberDetailsDto verifyEmployeeProjectId(long projectId)
	{
		UserPrincipal usr= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ProjectBelongers pm= rep.findByProjectIdAndEmployeeId(projectId, usr.getEmployeeId());
		
		MemberDetailsDto dto= new MemberDetailsDto();
		dto.setEmployeeId(pm.getEmployeeId());
		dto.setMailId(pm.getMailId());
		dto.setProjectId(pm.getProjectId());
		dto.setRole(pm.getRole());
		dto.setUserName(pm.getUserName());
		
		return dto;
	}

	public MemberDetailsDto verifyEmployeeProjectId(long projectId, long employeeId)
	{	
		ProjectBelongers pm= rep.findByProjectIdAndEmployeeId(projectId, employeeId);
		
		MemberDetailsDto dto= new MemberDetailsDto();
		dto.setEmployeeId(pm.getEmployeeId());
		dto.setMailId(pm.getMailId());
		dto.setProjectId(pm.getProjectId());
		dto.setRole(pm.getRole());
		dto.setUserName(pm.getUserName());
		
		return dto;
	}
}
