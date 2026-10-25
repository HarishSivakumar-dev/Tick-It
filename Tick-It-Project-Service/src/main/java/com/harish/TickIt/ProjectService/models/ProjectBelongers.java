package com.harish.TickIt.ProjectService.models;

import java.time.LocalDateTime;

import com.harish.TickIt.ProjectService.enums.ProjectRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Project_Membership")
public class ProjectBelongers
{
	@Id
	private Long employeeId;
	private Long projectId;
	private ProjectRole role;
	private boolean isActive;
	private LocalDateTime assignedDate;
	private LocalDateTime relievedDate;
	private String mailId;
	private String userName;
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	
	
}
