package com.harish.TickIt.Exceptions;


public class ProfileNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ProfileNotFoundException(String message)
	{
		super(message);
	}

}

