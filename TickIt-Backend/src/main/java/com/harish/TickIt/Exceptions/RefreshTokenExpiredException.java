package com.harish.TickIt.Exceptions;

public class RefreshTokenExpiredException extends Exception 
{
	private static final long serialVersionUID = 1L;
	public RefreshTokenExpiredException(String message) 
	{
		super(message);
	}
}
