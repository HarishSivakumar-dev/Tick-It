package com.harish.TickIt.UserService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import com.harish.TickIt.UserService.dtos.TicketDetailsDto;
import com.harish.TickIt.UserService.dtos.TicketUpdateDto;
import com.harish.TickIt.UserService.dtos.UserProfileDto;
import com.harish.TickIt.UserService.feign.TicketFeignClient;
import com.harish.TickIt.UserService.models.UserProfile;
import com.harish.TickIt.UserService.repos.UserProfileRepo;
import com.harish.TickIt.UserService.wrapperimpl.ResponseWrapperImpl;

@Service
public class UserService
{
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	@Autowired
	private ResponseWrapperImpl responseWrapper;
	
	@Autowired
	private TicketFeignClient ticketFeignClient;
	
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
	
	public String createTicket(TicketDetailsDto dto)
	{
		ResponseEntity<String> st=ticketFeignClient.ticketCreation(dto);
		return st.getBody();
	}
	
	public String deleteTicket(int ticketId)
	{
		ResponseEntity<String> st=ticketFeignClient.deleteTicket(ticketId);
		return st.getBody();
	}
	
	public String updateTicket(TicketUpdateDto dto)
	{
		ResponseEntity<String> st=ticketFeignClient.updateTicket(dto);
		return st.getBody();
	}
	
	public List<com.harish.TickIt.UserService.dtos.TicketResponseDto> getProjectTickets(int projectId)
	{
		ResponseEntity<List<com.harish.TickIt.UserService.dtos.TicketResponseDto>> st=ticketFeignClient.getProjectTickets(projectId);
		return st.getBody();
	}

}
