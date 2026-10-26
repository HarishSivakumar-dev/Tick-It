package com.harish.TickIt.ProjectService.dtos;

import com.harish.TickIt.ProjectService.enums.ProjectRole;

public class AddMembersDto
{
	private Long employeeId;
	private String mailId;
	private String userName;
	private ProjectRole role;
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ProjectRole getRole() {
		return role;
	}
	public void setRole(ProjectRole role) {
		this.role = role;
	}

}
