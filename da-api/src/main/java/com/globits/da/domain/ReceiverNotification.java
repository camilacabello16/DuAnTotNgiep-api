package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_receiver_notification")
@XmlRootElement
public class ReceiverNotification extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "notification_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Notification notification;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;
    @Column(name = "isRead")
    private Integer isRead ;
	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
