package com.harish.TickIt.Authentication;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import com.harish.TickIt.dtos.UserDetailsDto;
import com.harish.TickIt.dtos.UserProfileDto;
import com.harish.TickIt.feign.UserServiceFeign;
import com.harish.TickIt.models.Roles;
import com.harish.TickIt.repositories.RoleRepo;
import com.harish.TickIt.repositories.UserRegRepo;
import com.harish.TickIt.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuth2SuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRegRepo rep;
	@Autowired
	private RoleRepo rolerep;
	@Autowired
	private UserServiceFeign userServiceFeign;
	@Autowired
	private com.harish.TickIt.feign.ProjectServiceFeign project;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException
	{
		OAuth2User user= (OAuth2User) authentication.getPrincipal();
		String email= user.getAttribute("email");
		String name= user.getAttribute("name");
		
		Optional<com.harish.TickIt.models.UserRegistration> optionalUser= rep.findByEmail(email);
		com.harish.TickIt.models.UserRegistration usr;
		String token;
		
		if(optionalUser.isPresent())
		{
			usr= optionalUser.get();
			token= jwtUtil.generateToken(usr);
		}
		else
		{
			Roles rl= rolerep.findByRoleName("ROLE_USER").orElseGet(() -> {
				Roles newRole = new Roles("ROLE_USER");
				return rolerep.save(newRole);
			});
			
			usr= new com.harish.TickIt.models.UserRegistration();
			usr.setEmail(email);
			usr.setRegistrationDate(java.time.LocalDateTime.now());
			usr.setUserName(name);
			usr.getRoles().add(rl);
			
			com.harish.TickIt.models.UserRegistration newusr=rep.save(usr);
			
			UserProfileDto updto= new UserProfileDto();
			updto.setEmail(newusr.getEmail());
			updto.setUserName(newusr.getUserName());
			updto.setEmployeeId(newusr.getId());
			updto.setDepartment(null); // Set department to null or any default value
			updto.setProfileCreatedAt(newusr.getRegistrationDate());
			
			String res=userServiceFeign.createUserProfile(updto).getBody();
			
			UserDetailsDto dt= new UserDetailsDto();
			dt.setEmail(newusr.getEmail());
			dt.setUserId(newusr.getId());
			dt.setUserName(newusr.getUserName());
		
			String resp= project.saveUserDetails(dt).getBody();
			
			System.out.println("User profile creation response: " + res);
			System.out.println("Project service response: " + resp);
			token= jwtUtil.generateToken(newusr);	
		}	
		
		response.setHeader("Authorization", "Bearer "+token);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write("{\"message\": \"Login successful\"}"
				+ ",\"token\": \""+token+"\"}");
		
		
	}

}
