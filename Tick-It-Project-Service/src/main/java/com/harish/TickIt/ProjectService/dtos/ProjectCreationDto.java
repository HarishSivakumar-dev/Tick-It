package com.harish.TickIt.ProjectService.dtos;

import com.harish.TickIt.ProjectService.enums.ProjectStatus;

public class ProjectCreationDto
{
	private Long projectId;
	private String projectName;
	private String projectDescription;
	private ProjectStatus status;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
