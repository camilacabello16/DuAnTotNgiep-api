package com.globits.da.Template.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.domain.Card;
import com.globits.da.domain.Task;
import com.globits.da.dto.TaskDto;


public class TemplateCardDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String status;
	
	private Integer viewIndex;

	private TemplateWorkSpaceDto workSpace;

	private List<TemplateTaskDto> tasks;
	
	public TemplateCardDto() {}
	public TemplateCardDto(TemplateCard entity) {
		
	}
	public TemplateCardDto(TemplateCard entity,boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			this.createDate = entity.getCreateDate();
			this.createdBy = entity.getCreatedBy();
			this.modifyDate = entity.getModifyDate();
			this.modifiedBy = entity.getModifiedBy();
			this.name = entity.getName();
			this.status = entity.getStatus();
			this.viewIndex = entity.getViewIndex();
			if(entity.getTemplateTasks()!=null&&entity.getTemplateTasks().size()>0&&check) {
				tasks = new ArrayList<>();
				for(TemplateTask task: entity.getTemplateTasks()) {
					this.tasks.add(new TemplateTaskDto(task,false));
				}
			}
		}
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

	public Integer getViewIndex() {
		return viewIndex;
	}

	public void setViewIndex(Integer viewIndex) {
		this.viewIndex = viewIndex;
	}

	public TemplateWorkSpaceDto getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(TemplateWorkSpaceDto workSpace) {
		this.workSpace = workSpace;
	}

	public List<TemplateTaskDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<TemplateTaskDto> tasks) {
		this.tasks = tasks;
	} 
	
	
}
