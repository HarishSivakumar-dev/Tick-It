package com.harish.TickIt.ProjectService.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;

@RestController
@RequestMapping("/app")
public class ProjectMemberController
{
	@GetMapping("/projects/get/members")
	public ResponseEntity<List<UserDetailsDto>> getAllAvailableMembers()
	{
		//I will declare this definition with service implementation on 17/06/2026 
		return null;
		
	}

}
