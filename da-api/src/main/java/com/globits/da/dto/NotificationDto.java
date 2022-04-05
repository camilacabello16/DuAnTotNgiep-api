package com.globits.da.dto;

import org.joda.time.LocalDateTime;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Notification;

public class NotificationDto extends BaseObjectDto {
	private Integer type;
	private String content;
	private LocalDateTime creatDate;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(LocalDateTime creatDate) {
		this.creatDate = creatDate;
	}
	public NotificationDto() {}
	
	public NotificationDto(Notification entity) {
		this.id = entity.getId();
		this.createDate = entity.getCreateDate();
		this.createdBy = entity.getCreatedBy();
		this.modifyDate = entity.getModifyDate();
		this.modifiedBy = entity.getModifiedBy();
		this.type = entity.getType();
		this.content = entity.getContent();
	}
}
