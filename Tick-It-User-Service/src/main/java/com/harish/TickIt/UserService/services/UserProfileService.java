package com.harish.TickIt.UserService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.dtos.UserProfileDto;
import com.harish.TickIt.UserService.models.UserProfile;
import com.harish.TickIt.UserService.repos.UserProfileRepo;
import com.harish.TickIt.UserService.wrapperimpl.ResponseWrapperImpl;

@Service
public class UserProfileService
{
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	@Autowired
	private ResponseWrapperImpl responseWrapper;
	
	public ProfileResponseDto userProfileRetriever()
	{
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfile prof=userProfileRepo.findByEmail(name).orElseThrow(()->new RuntimeException("User not found"));
		
		return responseWrapper.getProfileResponseWrapper(prof);
		
	}
	
	public String createUserProfile(UserProfileDto dto)
	{
		UserProfile userProfile=new UserProfile();
		userProfile.setName(dto.getUserName());
		userProfile.setEmail(dto.getEmail());
		userProfile.setEmployeeId(dto.getId());
		userProfile.setRole(dto.getRole());
		userProfile.setDepartment(dto.getDepartment());
		userProfile.setAccountCreationDate(dto.getRegistrationDate());
		
		userProfileRepo.save(userProfile);
		
		return "User profile created successfully";
	}

}
