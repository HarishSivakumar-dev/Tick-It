package com.harish.TickIt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.TickIt.dtos.UserFeignResponse;
import com.harish.TickIt.dtos.UserLoginDto;
import com.harish.TickIt.dtos.UserRegDto;
import com.harish.TickIt.services.AuthService;

@RestController
@RequestMapping("/api")
public class UserAuthController 
{
	@Autowired
	private AuthService userService;
	
	@PostMapping("/user/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegDto dto)
	{
		return ResponseEntity.status(200).body(userService.registerUser(dto));
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginDto dto)
	{
		return ResponseEntity.status(200).body(userService.loginUser(dto));
	}
	
	@GetMapping("/user/get/details/{userId}")
	public ResponseEntity<UserFeignResponse> getUserDetails(@PathVariable Long userId) throws Exception
	{
		return ResponseEntity.status(200).body(userService.returnUserDetails(userId));
	}
}
