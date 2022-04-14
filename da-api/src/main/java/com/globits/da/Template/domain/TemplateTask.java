package com.globits.da.Template.domain;

import java.util.Date;
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
import com.globits.da.domain.Card;
import com.globits.da.domain.Comment;

@Entity
@Table(name = "tbl_template_task")
@XmlRootElement
public class TemplateTask extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	
	@Column(name ="start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "view_index")	
	private Integer viewIndex;
	
	@ManyToOne
	@JoinColumn(name = "template_card_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private TemplateCard templateCard;

	
	public Integer getViewIndex() {
		return viewIndex;
	}

	public void setViewIndex(Integer viewIndex) {
		this.viewIndex = viewIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public TemplateCard getTemplateCard() {
		return templateCard;
	}

	public void setTemplateCard(TemplateCard templateCard) {
		this.templateCard = templateCard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
