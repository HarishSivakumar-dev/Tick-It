package com.harish.TickIt.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.Exceptions.ProfileNotFoundException;
import com.harish.TickIt.Exceptions.UserAlreadyRegisteredException;
import com.harish.TickIt.dtos.UserProfDto;
import com.harish.TickIt.dtos.UserRegDto;
import com.harish.TickIt.models.UserProfile;
import com.harish.TickIt.models.UserRegistration;
import com.harish.TickIt.repositories.UserProfileRepo;
import com.harish.TickIt.repositories.UserRegRepo;

@Service
public class UserService
{
	@Autowired
	private UserProfileRepo userProfileRepo;
	@Autowired
	private UserRegRepo rep;
	
	BCryptPasswordEncoder bpe= new BCryptPasswordEncoder(12);
	

	public UserProfile getUserProfile(UserProfDto dto)
	{
		Optional<UserProfile> up=userProfileRepo.findByUserId(dto.getUserId());
		if(up.isEmpty()) throw new ProfileNotFoundException("Profile not found !");
		
		return up.get();
	}

	public String registerUser(UserRegDto dto)
	{
		if(rep.findByUserName(dto.getUserName()).isPresent())
		{
			throw new UserAlreadyRegisteredException("user already in the db");
		}
		
		if(!dto.getEmail().contains("@company.com")) return "Domain not Allowed";
		
		//multi-DB conn, retrival of data from the HR-DB for roles data, which will be used for setting the roles of the user registration 
		
		UserRegistration ur= new UserRegistration();
		ur.setEmail(dto.getEmail());
		ur.setPassword(bpe.encode(dto.getPassword()));
		ur.setRegistrationDate(LocalDateTime.now());
		ur.setUserName(dto.getUserName());
		ur.setRoles(null);
		
		rep.save(ur);
		
		
		return "Registered";
		
	}
	
}
