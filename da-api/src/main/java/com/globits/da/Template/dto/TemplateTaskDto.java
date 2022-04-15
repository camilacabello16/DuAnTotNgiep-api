package com.globits.da.Template.dto;

import java.util.ArrayList;
import java.util.Date;


import com.globits.core.dto.BaseObjectDto;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Task;
import com.globits.da.dto.CardDto;
import com.globits.da.dto.CommentDto;


public class TemplateTaskDto extends BaseObjectDto {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Date startDate;
	private Date endDate;
	private Integer viewIndex;	
	private TemplateCardDto templateCard;
	
	public TemplateTaskDto() {}

	public TemplateTaskDto(TemplateTask entity) {
		if(entity!=null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.startDate = entity.getStartDate();
			this.endDate = entity.getEndDate();
			this.viewIndex = entity.getViewIndex();
		}
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
	public Integer getViewIndex() {
		return viewIndex;
	}
	public void setViewIndex(Integer viewIndex) {
		this.viewIndex = viewIndex;
	}
	public TemplateCardDto getTemplateCard() {
		return templateCard;
	}
	public void setTemplateCard(TemplateCardDto templateCard) {
		this.templateCard = templateCard;
	}
	
	
}
