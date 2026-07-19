package com.harish.TickIt.UserService.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.harish.TickIt.UserService.authentication.UserPrincipal;
import com.harish.TickIt.UserService.dtos.ProfileDto;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.dtos.ProfileUpdateDto;
import com.harish.TickIt.UserService.dtos.UserProfileDto;
import com.harish.TickIt.UserService.models.UserProfile;
import com.harish.TickIt.UserService.repos.UserProfileRepo;
import com.harish.TickIt.UserService.wrapperimpl.ResponseWrapperImpl;
import jakarta.transaction.Transactional;

@Service
public class UserService
{
	@Autowired
	private UserProfileRepo userProfileRepo;
	@Autowired
	private ResponseWrapperImpl responseWrapper;
	@Autowired
	private CloudinaryService cloud;
	
	public ProfileResponseDto userProfileRetriever()
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserProfile prof=userProfileRepo.findByEmployeeId(prin.getEmployeeId()).orElseThrow(()->new RuntimeException("User not found"));
		
		return responseWrapper.getProfileResponseWrapper(prof);
		
	}
	
	public String createUserProfile(UserProfileDto dto)
	{
		UserProfile userProfile=new UserProfile();
		userProfile.setUserName(dto.getUserName());
		userProfile.setEmail(dto.getEmail());
		userProfile.setEmployeeId(dto.getEmployeeId());
		userProfile.setDepartment(dto.getDepartment());
		userProfile.setDesignation(dto.getDesignation());
		userProfile.setProfileCreatedAt(dto.getProfileCreatedAt());
		userProfile.setProfileCompleted(Boolean.FALSE);
		
		userProfileRepo.save(userProfile);
		
		return "User profile created successfully";
	}
	
	public ProfileDto getBasicUserProfile()
	{
		ProfileResponseDto userProfile=this.userProfileRetriever();
		
		ProfileDto profileDto = new ProfileDto();
		profileDto.setName(userProfile.getUserName());
		profileDto.setEmail(userProfile.getEmail());
		profileDto.setEmployeeId(userProfile.getEmployeeId());
		profileDto.setDepartment(userProfile.getDepartment());
		profileDto.setProfilePictureUrl(userProfile.getProfilePictureUrl());
		
		return profileDto;
	}

	public Boolean getUserDetails(long employeeId)
	{
		Optional<UserProfile> usr= userProfileRepo.findByEmployeeId(employeeId);
		
		if(usr.isEmpty())
		{
			return false;
		}
		else
		{
			if(usr.get().getDesignation().equals("PROJECT_MANAGER"))
				return true;
			else
			return false;
		}	
	}

	@Transactional
	public String updateUserProfile(ProfileUpdateDto dto)
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserProfile userProfile=userProfileRepo.findByEmployeeId(prin.getEmployeeId()).orElseThrow(()->new RuntimeException("User not found"));
		
		if(dto.getAddress()!=null)
			userProfile.setAddress(dto.getAddress());
		if(dto.getContactNumber()!=null)
			userProfile.setContactNumber(dto.getContactNumber());
		if(dto.getDateOfBirth()!=null)
			userProfile.setDateOfBirth(dto.getDateOfBirth());
		if(dto.getBio()!=null)
			userProfile.setBio(dto.getBio());
		if(dto.getGender()!=null)
			userProfile.setGender(dto.getGender());
		if(dto.getProfilePicture()!=null)
		{
			String op= cloud.photoUploaded(dto.getProfilePicture(), userProfile.getEmployeeId());
			userProfile.setProfilePictureUrl(op);
		}
		
		if(userProfile.getContactNumber()!=null && userProfile.getBio()!=null && userProfile.getProfilePictureUrl()!=null && userProfile.getUserName()!=null)
		{
			if(!userProfile.getContactNumber().isBlank() && !userProfile.getBio().isBlank() && !userProfile.getProfilePictureUrl().isBlank() && !userProfile.getUserName().isBlank())
				userProfile.setProfileCompleted(Boolean.TRUE);
			else
				userProfile.setProfileCompleted(Boolean.FALSE);
		}
		else
		{
			userProfile.setProfileCompleted(Boolean.FALSE);
		}
		
		userProfile.setProfileUpdatedAt(LocalDateTime.now());
		
		return "Updated !";
	}

	@Transactional
	public String updateProfilePicture(MultipartFile profilePicture)
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserProfile userProfile=userProfileRepo.findByEmployeeId(prin.getEmployeeId()).orElseThrow(()->new RuntimeException("User not found"));
		
		String url=cloud.photoUpdate(profilePicture, userProfile.getEmployeeId());
		
		userProfile.setProfilePictureUrl(url);
		
		return "Profile picture updated successfully";
	}

	@Transactional
	public String deleteProfilePicture()
	{
		UserPrincipal prin= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserProfile user= userProfileRepo.findByEmployeeId(prin.getEmployeeId()).orElseThrow(()->new RuntimeException("User not found"));
		user.setProfilePictureUrl(null);
		String s=cloud.photoDelete(user.getEmployeeId());
		
		return "Profile picture deleted successfully" + s;
	}

	public ProfileResponseDto getEmployeeDetails(long employeeId)
	{
		UserProfile usr= userProfileRepo.findByEmployeeId(employeeId).orElseThrow(()-> new RuntimeException("NO USER FOUND !"));
		return responseWrapper.getProfileResponseWrapper(usr);
	}

	public List<ProfileDto> searchProfiles(String query)
	{
		List<ProfileDto> dt= userProfileRepo.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query)
											.stream()
											.map(r->{
												ProfileDto dto= new ProfileDto();
												dto.setName(r.getUserName());
												dto.setEmail(r.getEmail());
												dto.setEmployeeId(r.getEmployeeId());
												dto.setDepartment(r.getDepartment());
												dto.setProfilePictureUrl(r.getProfilePictureUrl());
												
												return dto;
											})
											.toList();
		return dt;
	}

	public List<ProfileDto> searchProfilesByFilter(String department, String designation)
	{
		List<ProfileDto> dt= userProfileRepo.findBasedOnFilter(department, designation)
											.stream()
											.map(r->{
												ProfileDto dto= new ProfileDto();
												dto.setName(r.getUserName());
												dto.setEmail(r.getEmail());
												dto.setEmployeeId(r.getEmployeeId());
												dto.setDepartment(r.getDepartment());
												dto.setProfilePictureUrl(r.getProfilePictureUrl());
												
												return dto;
											})
											.toList();
		
		return dt;
	}	
}


