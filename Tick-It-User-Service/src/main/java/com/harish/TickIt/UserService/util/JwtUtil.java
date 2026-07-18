package com.harish.TickIt.UserService.util;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String secretKey;
	
	public Boolean validateToken(String token)
	{
		SecretKey secretKeyObj = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
		return Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token) !=null;
	}
	
	public String getUsernameFromToken(String token)
	{
		SecretKey secretKeyObj = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
		return Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token).getPayload().getSubject();
	}
	
	public java.util.Set<String> getRolesFromToken(String token)
	{
		SecretKey secretKeyObj = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
		java.util.List<?> role= Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token).getPayload().get("roles", java.util.List.class);
		
		java.util.Set<String> ro= role.stream()
						   .map(r -> r.toString())
						   .collect(java.util.stream.Collectors.toSet());	
		
		return ro;
	}

	public Integer getEmployeeId(String token)
	{
		SecretKey secretKeyObj = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
		return Jwts.parser().verifyWith(secretKeyObj).build().parseSignedClaims(token).getPayload().get("employeeId", Integer.class);
	}
	
	

}
