package com.globits.da.domain;

import java.util.Date;
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
@Table(name = "tbl_notification")
@XmlRootElement
public class Notification extends BaseObject {
	private static final long serialVersionUID = 1L;
	@Column(name = "type")
	private Integer  type;
	
	
	@Column(name = "content")
	private String content;

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

	
}
