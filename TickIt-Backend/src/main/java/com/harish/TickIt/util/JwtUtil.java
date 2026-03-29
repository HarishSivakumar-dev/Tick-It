package com.harish.TickIt.util;

import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import com.harish.TickIt.models.UserRegistration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	private static final String SECRET_KEY= "har22dk@@*(ukl%^)bhdim?:>{qwfqqqcbm^!(@36831737IKNB)}jdch%&*!38372819(&^^";
	SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	public String generateToken(UserRegistration user)
	{
		Set<String> roles= user.getRoles().stream()
										  .map(r->r.getRoleName())
										  .collect(java.util.stream.Collectors.toSet());
		return Jwts.builder()
				.subject(user.getUserName())
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.claim("roles", roles)
				.expiration(new java.util.Date(System.currentTimeMillis() + 3600000))
				.issuedAt(new java.util.Date(System.currentTimeMillis()))
				.compact();
	}
	
	public Set<String> getRolesFromToken(String token)
	{
		Set<?> role= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", Set.class);
		
		Set<String> ro= role.stream()
						   .map(r -> r.toString())
						   .collect(java.util.stream.Collectors.toSet());	
		
		return ro;
	}

	public boolean validateToken(String token) 
	{
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token) != null;
	}

	public String getUsernameFromToken(String token)
	{
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
	}
	

}
