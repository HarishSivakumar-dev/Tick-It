package com.harish.TickIt.UserService.wrapperimpl;

import org.springframework.stereotype.Component;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.models.UserProfile;

@Component
public class ResponseWrapperImpl implements com.harish.TickIt.UserService.wrappers.ProfileWrapper
{

	@Override
	public ProfileResponseDto getProfileResponseWrapper(UserProfile userProfile)
	{
		ProfileResponseDto responseDto=new ProfileResponseDto();
		
		responseDto.setName(userProfile.getName());
		responseDto.setEmail(userProfile.getEmail());
		responseDto.setEmployeeId(userProfile.getEmployeeId());
		responseDto.setDepartment(userProfile.getDepartment());
		responseDto.setContactNumber(userProfile.getContactNumber());
		responseDto.setProfilePictureUrl(userProfile.getProfilePictureUrl());
		responseDto.setAddress(userProfile.getAddress());
		responseDto.setDateOfBirth(userProfile.getDateOfBirth());
		responseDto.setGender(userProfile.getGender());
		responseDto.setAccountCreationDate(userProfile.getAccountCreationDate());
		responseDto.setNoOfTicketsAssigned(userProfile.getNoOfTicketsAssigned());
		responseDto.setNoOfTicketsResolved(userProfile.getNoOfTicketsResolved());
		responseDto.setNoOfTicketsActionPending(userProfile.getNoOfTicketsActionPending());
		responseDto.setLastLoginTime(userProfile.getLastLoginTime());
		responseDto.setAccountCreationDate(userProfile.getAccountCreationDate());
		responseDto.setAccountStatus(userProfile.getAccountStatus());
		responseDto.setNoOfTicketsExcalated(userProfile.getNoOfTicketsExcalated());
		responseDto.setStrikeCount(userProfile.getStrikeCount());
		responseDto.setLastStrikeDate(userProfile.getLastStrikeDate());
		responseDto.setPerformanceRating(userProfile.getPerformanceRating());
		
		return responseDto;
	}

}
