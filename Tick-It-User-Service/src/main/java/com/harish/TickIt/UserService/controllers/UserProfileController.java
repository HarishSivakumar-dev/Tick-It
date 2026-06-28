package com.harish.TickIt.UserService.controllers;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.UserService.dtos.ProfileDto;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.dtos.UserProfileDto;
import com.harish.TickIt.UserService.services.UserService;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController
{
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getProfile")
	public ResponseEntity<ProfileResponseDto> getUserProfile()
	{
		ProfileResponseDto userProfile=userService.userProfileRetriever();
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfile);
	}
	
	@PostMapping("/createProfile")
	public ResponseEntity<String> createUserProfile(@RequestBody UserProfileDto dto)
	{
		String res= userService.createUserProfile(dto);
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	@GetMapping("/getBasicUserProfile")
	public ResponseEntity<ProfileDto> getBasicUserProfile()
	{
		ProfileDto dt= userService.getBasicUserProfile();
		return ResponseEntity.status(HttpStatus.SC_OK).body(dt);
	}
	
	@GetMapping("/user/details")
	public ResponseEntity<Boolean> getUserDetails(@RequestParam long projectManagerId)
	{
		Boolean res= userService.getUserDetails(projectManagerId);
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	

}
