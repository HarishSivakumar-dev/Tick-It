package com.harish.TickIt.ProjectService.dtos;

import java.time.LocalDateTime;
import com.harish.TickIt.ProjectService.enums.ProjectRole;

public class MemberDetailsDto
{
	private Long projectId;
	private Long employeeId;
	private String mailId;
	private String userName;
	private ProjectRole role;
	private boolean isActive;
	private LocalDateTime assignedDate;
	private LocalDateTime relievedDate;
	
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
	public ProjectRole getRole() {
		return role;
	}
	public void setRole(ProjectRole role) {
		this.role = role;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public LocalDateTime getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDateTime assignedDate) {
		this.assignedDate = assignedDate;
	}
	public LocalDateTime getRelievedDate() {
		return relievedDate;
	}
	public void setRelievedDate(LocalDateTime relievedDate) {
		this.relievedDate = relievedDate;
	}
	
}
