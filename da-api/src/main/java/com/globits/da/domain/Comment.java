package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_comment")
@XmlRootElement
public class Comment extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private User user;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
