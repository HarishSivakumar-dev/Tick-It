package com.harish.TickIt.Exceptions;


@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler
{
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public org.springframework.http.ResponseEntity<String> handleAllExceptions(Exception ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
				.body("{\"error\": \"An unexpected error occurred: " + ex.getMessage() + "\"}");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public org.springframework.http.ResponseEntity<String> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex)
	{
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
		});
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST)
				.body("{\"error\": \"Validation failed: " + errors.toString() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public org.springframework.http.ResponseEntity<String> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN)
				.body("{\"error\": \"Access Denied: " + ex.getMessage() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
	public org.springframework.http.ResponseEntity<String> handleAuthenticationException(org.springframework.security.core.AuthenticationException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
				.body("{\"error\": \"Unauthorized: " + ex.getMessage() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
	public org.springframework.http.ResponseEntity<String> handleMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED)
				.body("{\"error\": \"Method Not Allowed: " + ex.getMessage() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
	public org.springframework.http.ResponseEntity<String> handleMissingParamsException(org.springframework.web.bind.MissingServletRequestParameterException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST)
				.body("{\"error\": \"Bad Request: Missing parameter - " + ex.getParameterName() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(com.harish.TickIt.Exceptions.UserAlreadyRegisteredException.class)
	public org.springframework.http.ResponseEntity<String> handleUserAlreadyRegisteredException(com.harish.TickIt.Exceptions.UserAlreadyRegisteredException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT)
				.body("{\"error\": \"Conflict: " + ex.getMessage() + "\"}");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
	public org.springframework.http.ResponseEntity<String> handleBadCredentialsException(org.springframework.security.authentication.BadCredentialsException ex)
	{
		return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
				.body("{\"error\": \"Unauthorized: " + ex.getMessage() + "\"}");
	}
	
}
