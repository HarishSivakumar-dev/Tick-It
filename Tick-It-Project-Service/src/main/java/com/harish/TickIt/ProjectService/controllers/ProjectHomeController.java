package com.harish.TickIt.ProjectService.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.ProjectService.dtos.MemberDetailsDto;
import com.harish.TickIt.ProjectService.dtos.ProjectCreationDto;
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
	public ResponseEntity<List<ProjectResponseDto>> getAllProjects()
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getAllProjects());
	}
	
	@PostMapping("/project/create")
	public ResponseEntity<String> createProject(@RequestBody ProjectCreationDto dto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ps.projectCreation(dto));
	}
	
	@PostMapping("/project/update/details")
	public ResponseEntity<String> updateProject(@RequestBody ProjectCreationDto dto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.projectDetailsUpdation(dto));
	}
	
	@PostMapping("/project/members/add")
	public ResponseEntity<String> addMembersIntoProject(@RequestBody List<MemberDetailsDto> dto)
	{	
		return ResponseEntity.status(HttpStatus.OK).body(ps.addMembersIntoProject(dto));
	}
	
	@GetMapping("/project/members/get/{projectid}")
	public ResponseEntity<List<UserProjectDetailsDto>> getProjectMembers(@PathVariable Long projectId)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getProjectMembers(projectId));
	}
	
}
