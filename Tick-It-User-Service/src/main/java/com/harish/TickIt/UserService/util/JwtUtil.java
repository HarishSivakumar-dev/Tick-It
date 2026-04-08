package com.harish.TickIt.UserService.util;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;

public class JwtUtil
{
	@Value("${jwt.secret}")
	private String secretKey;
	
	SecretKey secretKeyObj = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
	
	public Boolean validateToken(String token)
	{
		return Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token) !=null;
	}
	
	public String getUsernameFromToken(String token)
	{
		return Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token).getPayload().getSubject();
	}
	
	public java.util.Set<String> getRolesFromToken(String token)
	{
		java.util.Set<?> role= Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token).getPayload().get("roles", java.util.Set.class);
		
		java.util.Set<String> ro= role.stream()
						   .map(r -> r.toString())
						   .collect(java.util.stream.Collectors.toSet());	
		
		return ro;
	}
	
	

}
