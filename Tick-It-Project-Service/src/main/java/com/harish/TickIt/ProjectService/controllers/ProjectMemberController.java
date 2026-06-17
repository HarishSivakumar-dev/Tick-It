package com.harish.TickIt.ProjectService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;
import com.harish.TicktIt.ProjectService.dtos.UserProjectDto;
import com.harish.TicktIt.ProjectService.services.ProjectService;

@RestController
@RequestMapping("/app")
public class ProjectMemberController
{
	@Autowired
	private ProjectService ps;
	
	@GetMapping("/projects/get/members")
	public ResponseEntity<List<UserDetailsDto>> getAllAvailableMembers()
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getAllEmployeesUnassigned());
	}
	
	@PostMapping("/projects/add/default")
	public ResponseEntity<String> addDefaultUserDataPostRegistration(@RequestBody UserProjectDto dto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ps.userInfoPopulation(dto));
	}

}
