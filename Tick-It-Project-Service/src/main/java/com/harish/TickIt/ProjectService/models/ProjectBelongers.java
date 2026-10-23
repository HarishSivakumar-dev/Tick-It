package com.harish.TickIt.ProjectService.models;

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
	private int projectId;
	private ProjectRole role;
	private boolean isActive;
	
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
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
	
	
}
