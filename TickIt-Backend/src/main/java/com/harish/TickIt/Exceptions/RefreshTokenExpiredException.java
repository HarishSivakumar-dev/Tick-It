package com.harish.TickIt.Exceptions;

import java.util.UUID;

public class RefreshTokenExpiredException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	private UUID uuid;
	
	public RefreshTokenExpiredException(String message, UUID uuid) 
	{
		super(message);
		this.uuid=uuid;
	}
	
	public UUID getUuid() 
	{
		return uuid;
	}
}
