package com.harish.TickIt.models;

import com.harish.TickIt.enums.Department;
import com.harish.TickIt.enums.Designation;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_information")
public class EmployeeManagement
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String fullName;
	private String email;
	
	@Enumerated(jakarta.persistence.EnumType.STRING)
	private Designation designation;
	@Enumerated(jakarta.persistence.EnumType.STRING)
	private Department department;
	
	private Boolean AccountActivated;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getfullName() {
		return fullName;
	}
	public void setfullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Boolean getAccountActivated() {
		return AccountActivated;
	}
	public void setAccountActivated(Boolean accountActivated) {
		AccountActivated = accountActivated;
	}

}
