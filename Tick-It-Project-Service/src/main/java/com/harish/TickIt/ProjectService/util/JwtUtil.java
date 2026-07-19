package com.harish.TickIt.ProjectService.util;

import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	public boolean validateToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token) !=null;
	}
	public String getUsernameFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
	}
	
	public List<String> getRolesFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		List<?> st= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", List.class);
		
		List<String> roles= st.stream()
							   .map(r -> r.toString())
							   .toList();
		return roles;
	}
	public Long getEmployeeId(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("employeeId", Long.class);
	}

}
