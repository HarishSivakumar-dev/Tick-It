package com.harish.TickIt.TicketService.util;

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
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token) != null;
	}

}
