package com.harish.TickIt.util;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.harish.TickIt.Exceptions.RefreshTokenExpiredException;
import com.harish.TickIt.Exceptions.RefreshTokenInvalidException;
import com.harish.TickIt.Exceptions.RefreshTokenMalformedException;
import com.harish.TickIt.models.UserRegistration;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.refresh.secret}")
	private String REFRESH_SECRET;
	
	@Value("${jwt.expiration}")
	private Long EXPIRATION_TIME;
	
	@Value("${jwt.refresh.expiration}")
	private Long REFRESH_EXPIRATION;
	
	
	public String generateToken(UserRegistration user)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		Set<String> roles= user.getRoles().stream()
										  .map(r->r.getRoleName())
										  .collect(java.util.stream.Collectors.toSet());
		
		UUID accessUuid= UUID.randomUUID();
		
		return Jwts.builder()
				.subject(user.getUserName())
				.signWith(secretKey)
				.claim("roles", roles)
				.claim("employeeId", user.getEmployeeId())
				.claim("uuid", accessUuid)
				.expiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.issuedAt(new java.util.Date(System.currentTimeMillis()))
				.compact();
	}
	
	public Set<String> getRolesFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		List<?> role= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", List.class);
		
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
	
	public Long getUserId(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("employeeId", Long.class);
	}
	
	public String generateRefreshToken(UserRegistration user)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
		
		Set<String> st= user.getRoles()
							.stream()
							.map(r->r.toString())
							.collect(Collectors.toSet());
		
		return Jwts.builder()
				   .subject(user.getUserName())
				   .claim("roles", st)
				   .claim("employeeId", user.getId())
				   .signWith(secretKey)
				   .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
				   .issuedAt(new Date(System.currentTimeMillis()))
				   .compact();
	}
	
	public void verifyRefreshToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
		
		try
		{
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
		}
		catch(ExpiredJwtException e)
		{
			UUID uuid= UUID.fromString(e.getClaims().get("uuid", String.class));
			throw new RefreshTokenExpiredException("REFRESH TOKEN EXPIRED",uuid);
		}
		catch(MalformedJwtException e)
		{
			throw new RefreshTokenMalformedException("REFRESH TOKEN MALFORM");
		}
		catch(JwtException e)
		{
			throw new RefreshTokenInvalidException("INVALID REFRESH TOKEN");
		}
	}
	
	public UUID getUuidFromToken(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		String uuid= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("uuid", String.class);
		
		UUID uid= UUID.fromString(uuid);
		
		return uid;
	}

	public UUID getUuidFromRefresh(String token)
	{
		SecretKey secretKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
		String uuid= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("uuid", String.class);
		
		UUID uid= UUID.fromString(uuid);
		
		return uid;
	}
	

}
