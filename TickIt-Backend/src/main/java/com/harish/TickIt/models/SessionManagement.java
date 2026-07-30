package com.harish.TickIt.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RefreshToken
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	private Long employeeId;
	private String token;
	private Boolean revoked;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getRevoked() {
		return revoked;
	}
	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}
	
}
