package com.harish.TickIt.UserService.controllers;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.services.UserProfileService;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController
{
	
	@Autowired
	private UserProfileService userProfileService;
	
	public ResponseEntity<ProfileResponseDto> getUserProfile()
	{
		ProfileResponseDto userProfile=userProfileService.userProfileRetriever();
		
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfile);
	}
	
	

}
