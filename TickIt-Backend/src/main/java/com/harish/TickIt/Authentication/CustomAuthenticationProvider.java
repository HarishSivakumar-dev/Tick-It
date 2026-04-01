package com.harish.TickIt.Authentication;

import java.util.Optional;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.harish.TickIt.models.UserRegistration;
import com.harish.TickIt.repositories.UserRegRepo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
	@Autowired
	private UserRegRepo repo;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

	@Override
	public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		Optional<UserRegistration> user = repo.findByUserName(username);
		
		if(user.isPresent() && encoder.matches(password, user.get().getPassword()))
		{
			Set<SimpleGrantedAuthority> auth= user.get().getRoles().stream()
																   .map(r-> new SimpleGrantedAuthority(r.getRoleName()))
																   .collect(java.util.stream.Collectors.toSet());
																		   
			return new UsernamePasswordAuthenticationToken(username, null, auth );
		}
		else
		{
			throw new org.springframework.security.authentication.BadCredentialsException("Invalid username or password");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}

}
