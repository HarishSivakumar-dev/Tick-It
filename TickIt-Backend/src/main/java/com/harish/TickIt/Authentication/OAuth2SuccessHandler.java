package com.harish.TickIt.Authentication;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import com.harish.TickIt.models.UserRegistration;
import com.harish.TickIt.repositories.UserRegRepo;
import com.harish.TickIt.services.AuthService;
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
	private AuthService ser;

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
			UserRegistration reg= ser.registerUserOauth(email, name);
			token= jwtUtil.generateToken(reg);
		}	
		
		response.setHeader("Authorization", "Bearer "+token);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write("{\"message\": \"Login successful\"}"
				+ ",\"token\": \""+token+"\"}");
		
		
	}

}
