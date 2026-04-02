package com.harish.TickIt.util;

import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.harish.TickIt.models.UserRegistration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.expiration}")
	private Long EXPIRATION_TIME;
	
	
	public String generateToken(UserRegistration user)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		Set<String> roles= user.getRoles().stream()
										  .map(r->r.getRoleName())
										  .collect(java.util.stream.Collectors.toSet());
		return Jwts.builder()
				.subject(user.getUserName())
				.signWith(secretKey)
				.claim("roles", roles)
				.expiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.issuedAt(new java.util.Date(System.currentTimeMillis()))
				.compact();
	}
	
	public Set<String> getRolesFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		Set<?> role= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", Set.class);
		
		Set<String> ro= role.stream()
						   .map(r -> r.toString())
						   .collect(java.util.stream.Collectors.toSet());	
		
		return ro;
	}

	public boolean validateToken(String token) 
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token) != null;
	}

	public String getUsernameFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
	}
	

}
