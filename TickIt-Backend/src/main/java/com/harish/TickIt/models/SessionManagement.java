package com.harish.TickIt.models;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class SessionManagement
{
	@Id
	private UUID sessionID;
	
	private Long employeeID;
	private UUID accessTokenJti;
	private UUID refreshTokenJti;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private Boolean revoked;
	
	
	public UUID getSessionID() {
		return sessionID;
	}
	public void setSessionID(UUID sessionID) {
		this.sessionID = sessionID;
	}
	public Long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}
	public UUID getAccessTokenJti() {
		return accessTokenJti;
	}
	public void setAccessTokenJti(UUID accessTokenJti) {
		this.accessTokenJti = accessTokenJti;
	}
	public UUID getRefreshTokenJti() {
		return refreshTokenJti;
	}
	public void setRefreshTokenJti(UUID refreshTokenJti) {
		this.refreshTokenJti = refreshTokenJti;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Boolean getRevoked() {
		return revoked;
	}
	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}
	
}
