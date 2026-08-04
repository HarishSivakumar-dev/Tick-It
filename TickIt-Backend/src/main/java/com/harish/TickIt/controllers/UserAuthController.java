package com.harish.TickIt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harish.TickIt.dtos.RefreshDto;
import com.harish.TickIt.dtos.UserFeignResponse;
import com.harish.TickIt.dtos.UserLoginDto;
import com.harish.TickIt.dtos.UserRegDto;
import com.harish.TickIt.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController 
{
	@Autowired
	private AuthService userService;
	
	@PostMapping("/user/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegDto dto)
	{
		return ResponseEntity.status(200).body(userService.registerUser(dto).getUserName() + " registered successfully");
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginDto dto)
	{
		return ResponseEntity.status(200).body(userService.loginUser(dto));
	}
	
	@GetMapping("/user/get/details/{userId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserFeignResponse> getUserDetails(@PathVariable Long userId) throws Exception
	{
		return ResponseEntity.status(200).body(userService.returnUserDetails(userId));
	}
	
	@PostMapping("/user/refresh")
	public ResponseEntity<String> refreshController(@RequestBody RefreshDto dt)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.refreshTokenVerify(dt));
	}
	
	@PostMapping("/user/logout")
	public ResponseEntity<String> logOut()
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.logoutUser());
	}
	
	@PostMapping("/user/logout/all")
	public ResponseEntity<String> logOutAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.logoutAll());
	}
	
	
}
