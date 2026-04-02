package com.harish.TickIt.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.Exceptions.UserAlreadyRegisteredException;
import com.harish.TickIt.dtos.UserLoginDto;
import com.harish.TickIt.dtos.UserRegDto;
import com.harish.TickIt.models.Roles;
import com.harish.TickIt.models.UserRegistration;
import com.harish.TickIt.repositories.RoleRepo;
import com.harish.TickIt.repositories.UserRegRepo;
import com.harish.TickIt.util.JwtUtil;

@Service
public class AuthService
{
	@Autowired
	private UserRegRepo rep;
	@Autowired
	private RoleRepo rolerep;
	
	BCryptPasswordEncoder bpe= new BCryptPasswordEncoder(12);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String registerUser(UserRegDto dto)
	{
		if(rep.findByUserName(dto.getUserName()).isPresent() || rep.findByEmail(dto.getEmail()).isPresent())
		{
			throw new UserAlreadyRegisteredException("user already in the db");
		}
		
		Roles rl= rolerep.findByRoleName("ROLE_USER").orElseGet(() -> {
			Roles newRole = new Roles("ROLE_USER");
			return rolerep.save(newRole);
		});
	
		UserRegistration ur= new UserRegistration();
		ur.setEmail(dto.getEmail());
		ur.setPassword(bpe.encode(dto.getPassword()));
		ur.setRegistrationDate(LocalDateTime.now());
		ur.setUserName(dto.getUserName());
		ur.getRoles().add(rl);
		
		rep.save(ur);
		
		
		return "Registered";
		
	}
	
	public String loginUser(UserLoginDto dto)
	{
		authenticationManager.authenticate(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		
		String token=jwtUtil.generateToken(rep.findByUserName(dto.getUserName()).get());
		
		return token;
	}
	
}
