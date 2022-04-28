package com.globits.da.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name = "tbl_team_user")
@XmlRootElement
public class TeamUser extends BaseObject {
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	// @NotFound(action = NotFoundAction.IGNORE)
	private Team team;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	
}
