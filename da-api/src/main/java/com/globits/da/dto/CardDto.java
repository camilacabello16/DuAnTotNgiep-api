package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Card;
import com.globits.da.domain.Task;

public class CardDto extends BaseObjectDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String status;
	private Integer viewIndex;
	private WorkSpaceDto workSpace;
	private List<TaskDto> tasks;
	
	public CardDto() {}
	public CardDto(Card entity) {
		if(entity!=null) {
			this.id = entity.getId();
			this.createDate = entity.getCreateDate();
			this.createdBy = entity.getCreatedBy();
			this.modifyDate = entity.getModifyDate();
			this.modifiedBy = entity.getModifiedBy();
			this.name = entity.getName();
			this.status = entity.getStatus();
			this.viewIndex = entity.getViewIndex();
			if(entity.getTasks()!=null&&entity.getTasks().size()>0) {
				tasks = new ArrayList<>();
				for(Task task:entity.getTasks()) {
					this.tasks.add(new TaskDto(task));
				}
			}
		}
	}
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public WorkSpaceDto getWorkSpace() {
		return workSpace;
	}
	public void setWorkSpace(WorkSpaceDto workSpace) {
		this.workSpace = workSpace;
	}
	public List<TaskDto> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDto> tasks) {
		this.tasks = tasks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
