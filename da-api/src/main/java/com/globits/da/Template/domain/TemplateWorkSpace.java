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
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_template_workspace")
@XmlRootElement
public class TemplateWorkSpace extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private Integer type; // 0: Template chung do admin create, 1: Template cua nguoi dung

	@Column(name = "visibility")
	private Integer visibility;

	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<TemplateWorkSpace> childs; 
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private TemplateWorkSpace parent;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	@OneToMany(mappedBy = "templateWorkSpace", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<TemplateCard> templateCards; 
	
	
	public Set<TemplateCard> getTemplateCards() {
		return templateCards;
	}

	public void setTemplateCards(Set<TemplateCard> templateCards) {
		this.templateCards = templateCards;
	}

	public Set<TemplateWorkSpace> getChilds() {
		return childs;
	}

	public void setChilds(Set<TemplateWorkSpace> childs) {
		this.childs = childs;
	}

	public TemplateWorkSpace getParent() {
		return parent;
	}

	public void setParent(TemplateWorkSpace parent) {
		this.parent = parent;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

