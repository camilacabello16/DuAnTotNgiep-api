package com.globits.da.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_workspace")
@XmlRootElement
public class WorkSpace extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private Integer type;

	@Column(name = "visibility")
	private Integer visibility;

	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<WorkSpace> childs; 
	
	@OneToMany(mappedBy = "workSpace", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<WorkSpaceUser> workSpaceUser; 

	@ManyToOne
	@JoinColumn(name = "parent_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private WorkSpace parent;
	
	
	public Set<WorkSpace> getChilds() {
		return childs;
	}

	public void setChilds(Set<WorkSpace> childs) {
		this.childs = childs;
	}

	public WorkSpace getParent() {
		return parent;
	}

	public void setParent(WorkSpace parent) {
		this.parent = parent;
	}

	public Set<WorkSpaceUser> getWorkSpaceUser() {
		return workSpaceUser;
	}

	public void setWorkSpaceUser(Set<WorkSpaceUser> workSpaceUser) {
		this.workSpaceUser = workSpaceUser;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
