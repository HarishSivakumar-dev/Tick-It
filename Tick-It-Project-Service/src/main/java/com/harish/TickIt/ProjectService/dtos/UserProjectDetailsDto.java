package com.harish.TickIt.ProjectService.dtos;

import java.time.LocalDateTime;
import com.harish.TickIt.ProjectService.enums.ProjectRole;

public class UserProjectDetailsDto 
{
	private Long projectId;
	private Long employeeId;
	private String mailId;
	private String userName;
	private LocalDateTime assignedDate;
	private LocalDateTime relievedDate;
	private ProjectRole role;
	
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
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
	public LocalDateTime getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDateTime localDateTime) {
		this.assignedDate = localDateTime;
	}
	public LocalDateTime getRelievedDate() {
		return relievedDate;
	}
	public void setRelievedDate(LocalDateTime relievedDate) {
		this.relievedDate = relievedDate;
	}
	public ProjectRole getRole() {
		return role;
	}
	public void setRole(ProjectRole role) {
		this.role = role;
	}
	
}
