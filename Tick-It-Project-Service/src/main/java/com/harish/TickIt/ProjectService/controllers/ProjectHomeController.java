package com.harish.TickIt.ProjectService.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.ProjectService.dtos.MemberDetailsDto;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
import com.harish.TickIt.ProjectService.dtos.ProjectDetDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDetailsDto;
import com.harish.TickIt.ProjectService.services.ProjectService;

@RestController
@RequestMapping("/app")
public class ProjectHomeController
{	
	@Autowired
	private ProjectService ps;
	
	@GetMapping("/projects/get")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ProjectResponseDto>> getAllProjects()
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getAllProjects());
	}
	
	@PostMapping("/projects/create")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<String> createProject(@RequestBody ProjectCreationDto dto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ps.projectCreation(dto));
	}
	
	@PostMapping("/projects/update/details")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<String> updateProject(@RequestBody ProjectCreationDto dto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.projectDetailsUpdation(dto));
	}
	
	@PostMapping("/projects/members/add")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<String> addMembersIntoProject(@RequestBody List<MemberDetailsDto> dto)
	{	
		return ResponseEntity.status(HttpStatus.OK).body(ps.addMembersIntoProject(dto));
	}
	
	@GetMapping("/projects/members/get/{projectId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<UserProjectDetailsDto>> getProjectMembers(@PathVariable Long projectId)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getProjectMembers(projectId));
	}
	
	@GetMapping("/project/find")
	@PreAuthorize("hasRole('USER')")
	public Optional<ProjectDetDto> findProjectById(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam long projectId)
	{
		System.out.println("Token received: " + token);
		return ps.findProject(projectId);
	}
	
}
