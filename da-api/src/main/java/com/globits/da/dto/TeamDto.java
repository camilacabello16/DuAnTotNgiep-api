package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Team;
import com.globits.da.domain.TeamUser;
import com.globits.security.dto.UserDto;

public class TeamDto extends BaseObjectDto {

	private String name;
	private String code;
	private String description;
	private Long hostId;
	private List<TeamUserDto> teamUsers;
	private List<UserDto> users ;

	public TeamDto() {
	}

	public TeamDto(Team entity) {
		this(entity, true);
	}

	public TeamDto(Team entity, Boolean check) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.hostId = entity.getHostId();
			if (entity.getTeamUsers() != null && entity.getTeamUsers().size() > 0 && check) {
				this.teamUsers = new ArrayList<>();
				this.users = new ArrayList<UserDto>();
				for (TeamUser teamUser : entity.getTeamUsers()) {
					this.teamUsers.add(new TeamUserDto(teamUser, false));
					if (teamUser.getUser() != null) {
						this.users.add(new UserDto(teamUser.getUser()));
					}
				}
			}
		}
	}

	public List<TeamUserDto> getTeamUsers() {
		return teamUsers;
	}

	public void setTeamUsers(List<TeamUserDto> teamUsers) {
		this.teamUsers = teamUsers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

}
