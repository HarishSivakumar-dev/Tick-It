package com.harish.TickIt.Exceptions;

public class RefreshTokenInvalidException extends Exception
{
	private static final long serialVersionUID = 1L;
	public RefreshTokenInvalidException(String message)
	{
		super(message);
	}

}
