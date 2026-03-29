package com.harish.TickIt.Exceptions;


public class UserAlreadyRegisteredException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7810732837841493864L;
	
	public UserAlreadyRegisteredException(String message)
	{
		super(message);
	}

}
