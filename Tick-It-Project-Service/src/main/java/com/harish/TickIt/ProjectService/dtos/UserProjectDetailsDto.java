package com.harish.TicktIt.ProjectService.dtos;

import java.time.LocalDate;
import com.harish.TicktIt.ProjectService.enums.ProjectRole;

public class UserProjectDetailsDto 
{
	private Long projectId;
	private Long userId;
	private String mailId;
	private String userName;
	private LocalDate assignedDate;
	private LocalDate relievedDate;
	private ProjectRole role;
	
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public LocalDate getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
	public LocalDate getRelievedDate() {
		return relievedDate;
	}
	public void setRelievedDate(LocalDate relievedDate) {
		this.relievedDate = relievedDate;
	}
	public ProjectRole getRole() {
		return role;
	}
	public void setRole(ProjectRole role) {
		this.role = role;
	}
	
}
