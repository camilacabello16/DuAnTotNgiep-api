package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_workspace_user")
@XmlRootElement
public class WorkSpaceUser extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "workspace_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private WorkSpace workSpace;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "status") // 0: chua tham gia , 1: da tham gia
	private Integer status;

	public WorkSpace getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(WorkSpace workSpace) {
		this.workSpace = workSpace;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
}
