package com.harish.TicktIt.ProjectService.models;

import com.harish.TicktIt.ProjectService.enums.ProjectRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProjectMembers
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private long projectId;
	private long userId;
	private ProjectRole role;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
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

}
