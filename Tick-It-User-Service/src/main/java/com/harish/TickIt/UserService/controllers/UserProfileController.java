package com.harish.TickIt.UserService.controllers;

import java.util.List;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harish.TickIt.UserService.dtos.ProfileDto;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.dtos.ProfileUpdateDto;
import com.harish.TickIt.UserService.dtos.UserProfileDto;
import com.harish.TickIt.UserService.services.UserService;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController
{
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getProfile")
	@PreAuthorize("hasRole('USER')")
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
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProfileDto> getBasicUserProfile()
	{
		ProfileDto dt= userService.getBasicUserProfile();
		return ResponseEntity.status(HttpStatus.SC_OK).body(dt);
	}
	
	@GetMapping("/user/details")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Boolean> getUserDetails(@RequestParam long projectManagerId)
	{
		Boolean res= userService.getUserDetails(projectManagerId);
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	@PostMapping("/updateProfile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateUserProfile(@ModelAttribute ProfileUpdateDto dto)
	{
		String res= userService.updateUserProfile(dto);
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	@PutMapping("/updateProfilePicture")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateProfilePicture(@RequestParam MultipartFile profilePictureUrl)
	{
		String res= userService.updateProfilePicture(profilePictureUrl);
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	@DeleteMapping("/deleteProfilePicture")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> deleteProfilePicture()
	{
		String res= userService.deleteProfilePicture();
		return ResponseEntity.status(HttpStatus.SC_OK).body(res);
	}
	
	@GetMapping("/profile/{employeeId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProfileResponseDto> getEmployeeDetails(@PathVariable long employeeId)
	{
		ProfileResponseDto dt= userService.getEmployeeDetails(employeeId);
		return ResponseEntity.status(HttpStatus.SC_OK).body(dt);
	}
	
	@GetMapping("/profile/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ProfileDto>> searchProfiles(@RequestParam String query)
	{
		List<ProfileDto> dt= userService.searchProfiles(query);
		return ResponseEntity.status(HttpStatus.SC_OK).body(dt);
	}
	
	@GetMapping("/profile/search/filter")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ProfileDto>> searchProfilesByFilter(@RequestParam String department, @RequestParam String designation)
	{
		List<ProfileDto> dt= userService.searchProfilesByFilter(department, designation);
		return ResponseEntity.status(HttpStatus.SC_OK).body(dt);
	}

}
