package com.harish.TickIt.UserService.dtos;

import java.time.LocalDateTime;

public class UserProfileDto
{
	private Long employeeId;
	private String userName;
	private String email;
	private String department;
	private LocalDateTime profileCreatedAt;
	
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public LocalDateTime getProfileCreatedAt() {
		return profileCreatedAt;
	}
	public void setProfileCreatedAt(LocalDateTime profileCreatedAt) {
		this.profileCreatedAt = profileCreatedAt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}
