package com.harish.TickIt.Exceptions;

public class RefreshTokenMalformedException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public RefreshTokenMalformedException(String message)
	{
		super(message);
	}
}
