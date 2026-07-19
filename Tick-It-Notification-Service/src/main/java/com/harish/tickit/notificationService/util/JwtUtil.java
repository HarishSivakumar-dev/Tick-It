package com.harish.tickit.notificationService.util;

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
	private String secret;

	public Long extractUserId(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secret.getBytes());
		Long id=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("employeeId", Long.class);
		
		return id;
	}
	
	public Boolean validateToken(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secret.getBytes());
		
		try
		{
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public String getUsername(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secret.getBytes());
		
		String username= Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		return username;
	}
	
	public List<String> getRolesFromToken(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secret.getBytes());
		List<?> ls= Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("roles", List.class);
		
		List<String> res= ls.stream()
							.map(r->{
								return r.toString();
										
							})
							.toList();
		
		return res;			
	}
}
