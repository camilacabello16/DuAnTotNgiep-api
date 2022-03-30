package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Comment;
import com.globits.security.dto.UserDto;

public class CommentDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private UserDto user;
	private TaskDto task;
	
	public CommentDto() {}
	public CommentDto(Comment entity) {
		this(entity, true);
	}
	public CommentDto(Comment entity,boolean check) {
		if(entity!=null) {
			this.content = entity.getContent();
			if(entity.getUser()!=null&&entity.getUser().getId()!=null&&check) {
				this.user = new UserDto(entity.getUser());
			}
			if(entity.getTask()!=null&&entity.getTask().getId()!=null&&check) {
				this.task = new TaskDto(entity.getTask(),false);
			}
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public TaskDto getTask() {
		return task;
	}
	public void setTask(TaskDto task) {
		this.task = task;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
