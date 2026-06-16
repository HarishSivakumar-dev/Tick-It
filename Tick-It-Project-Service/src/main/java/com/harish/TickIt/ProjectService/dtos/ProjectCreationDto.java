package com.harish.TickIt.ProjectService.dtos;

import java.time.LocalDate;
import com.harish.TicktIt.ProjectService.enums.ProjectStatus;

public class ProjectCreationDto
{
	private String projectName;
	private String projectDescription;
	private long projectManagerId;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;
	private Boolean isActive;
	
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
	public long getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
	public Boolean isActive() {
		return isActive;
	}
	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

}
