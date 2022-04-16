package com.globits.da.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Task;
import com.globits.security.dto.UserDto;

public class TaskDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Date endDate;
	private Date startDate;
	private CardDto card;
	private Integer viewIndex;
	private List<CommentDto> comments;
	private UserDto user;
	public TaskDto() {}
	public TaskDto(Task entity) {
		if(entity!=null) {
			this.id = entity.getId();
			this.createDate = entity.getCreateDate();
			this.createdBy = entity.getCreatedBy();
			this.modifyDate = entity.getModifyDate();
			this.modifiedBy = entity.getModifiedBy();
			this.name = entity.getName();
			this.startDate = entity.getStartDate();
			this.endDate = entity.getEndDate();
			this.viewIndex = entity.getViewIndex();
			if(entity.getComments()!=null&&entity.getComments().size()>0) {
				this.comments = new ArrayList<>();
				for(Comment comment:entity.getComments()) {
					this.comments.add(new CommentDto(comment));
				}
			}
			if(entity.getUser()!=null&&entity.getUser().getId()!=null) {
				this.user = new UserDto(entity.getUser());
			}
		}
	}
	
	
	public Integer getViewIndex() {
		return viewIndex;
	}
	public void setViewIndex(Integer viewIndex) {
		this.viewIndex = viewIndex;
	}
	public List<CommentDto> getComments() {
		return comments;
	}
	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public CardDto getCard() {
		return card;
	}
	public void setCard(CardDto card) {
		this.card = card;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	

}
