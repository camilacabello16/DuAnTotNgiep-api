package com.globits.da.Template.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.da.domain.Task;
import com.globits.da.domain.WorkSpace;

@Entity
@Table(name = "tbl_template_card")
@XmlRootElement
public class TemplateCard extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "view_index")	
	private Integer viewIndex;
	@ManyToOne
	@JoinColumn(name = "template_workspace_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private TemplateWorkSpace templateWorkSpace;
	
	@OneToMany(mappedBy = "templateCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<TemplateTask> templateTasks; 
	
	

	public Integer getViewIndex() {
		return viewIndex;
	}

	public void setViewIndex(Integer viewIndex) {
		this.viewIndex = viewIndex;
	}

	public Set<TemplateTask> getTemplateTasks() {
		return templateTasks;
	}

	public void setTemplateTasks(Set<TemplateTask> templateTasks) {
		this.templateTasks = templateTasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TemplateWorkSpace getTemplateWorkSpace() {
		return templateWorkSpace;
	}

	public void setTemplateWorkSpace(TemplateWorkSpace templateWorkSpace) {
		this.templateWorkSpace = templateWorkSpace;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
