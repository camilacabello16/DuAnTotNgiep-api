package com.globits.da.dto.search;

import java.util.UUID;

public class WorkSpaceUserSearchDto extends SearchDto {
	
	private Long userId;
	private String role;
	private Integer status;
	private UUID parentId;
	
	
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
