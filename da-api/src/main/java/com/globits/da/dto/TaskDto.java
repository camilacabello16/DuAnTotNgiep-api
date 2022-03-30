package com.globits.da.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Task;

public class TaskDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Date endDate;
	private Date startDate;
	private CardDto card;
	private List<CommentDto> comments;
	public TaskDto() {}
	public TaskDto(Task entity) {
		this(entity, true);
	}
	public TaskDto(Task entity,boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.startDate = entity.getStartDate();
			this.endDate = entity.getEndDate();
			if(entity.getCard()!=null&&entity.getCard().getId()!=null&&check) {
				this.card = new CardDto(entity.getCard(),false);
			}
			if(entity.getComments()!=null&&entity.getComments().size()>0 &&check) {
				this.comments = new ArrayList<>();
				for(Comment comment:entity.getComments()) {
					this.comments.add(new CommentDto(comment,false));
				}
			}
		}
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
	
	

}
