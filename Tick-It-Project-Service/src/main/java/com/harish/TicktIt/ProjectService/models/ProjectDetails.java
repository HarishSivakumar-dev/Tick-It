package com.harish.TicktIt.ProjectService.models;

import java.time.LocalDate;
import com.harish.TicktIt.ProjectService.enums.ProjectStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProjectDetails
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long projectId;
	
	private String projectName;
	private String projectDescription;
	private long projectManagerId;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private boolean isActive;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;
	private String ManagerName;
	private String ManagerEmail;
	
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public String getManagerName() {
		return ManagerName;
	}

	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}

	public String getManagerEmail() {
		return ManagerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		ManagerEmail = managerEmail;
	}

}
