package com.harish.tickit.apigateway;

import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String secretKey;
	
	public Boolean verifyJwt(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes());
		Boolean res= Jwts.parser().verifyWith(key).build().parseSignedClaims(token) !=null;
		return res;
	}
	
	public List<String> getRoles(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes());
		List<?> ls = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("roles", List.class);
		
		List<String> res= ls.stream()
				            .map(i->{
				            	return i.toString();
				            })
				            .toList();
		return res;
	}
	
	public String getUserName(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes());
		
		String res= Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		return res;
	}
	
	public Integer getEmployeeId(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes());
		
		Integer res= Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("employeeId", Integer.class);
		return res;
	}
	
	public UUID getUuidFromToken(String token)
	{
		SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes());
		
		String uuidString= Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("uuid", String.class);
		return UUID.fromString(uuidString);
	}
	
}
