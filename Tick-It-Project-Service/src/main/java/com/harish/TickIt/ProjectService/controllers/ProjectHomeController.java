package com.harish.TickIt.ProjectService.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TicktIt.ProjectService.services.ProjectService;

@RestController
@RequestMapping("/app")
public class ProjectHomeController
{	
	@Autowired
	private ProjectService ps;
	
	@GetMapping("/get/projects")
	private ResponseEntity<List<ProjectResponseDto>> getAllProjects()
	{
		return ResponseEntity.status(HttpStatus.OK).body(ps.getAllProjects());
		
	}
	
}
