package com.harish.TickIt.ProjectService.models;

import java.time.LocalDate;
import com.harish.TickIt.ProjectService.enums.ProjectRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProjectMembers
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Long projectId;
	private Long userId;
	private String mailId;
	private String userName;
	private LocalDate assignedDate;
	private LocalDate relievedDate;
	
	@Enumerated(EnumType.STRING)
	private ProjectRole role;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public ProjectRole getRole() {
		return role;
	}
	public void setRole(ProjectRole role) {
		this.role = role;
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
	public Long getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
