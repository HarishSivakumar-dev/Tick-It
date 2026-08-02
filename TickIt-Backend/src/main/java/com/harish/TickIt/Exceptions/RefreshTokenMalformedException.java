package com.harish.TickIt.Exceptions;

public class RefreshTokenMalformedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public RefreshTokenMalformedException(String message)
	{
		super(message);
	}
}
