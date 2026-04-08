package com.harish.TickIt.UserService.wrappers;

import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.models.UserProfile;

public interface ProfileWrapper
{
	ProfileResponseDto getProfileResponseWrapper(UserProfile userProfile);

}
