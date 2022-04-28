package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.TeamUser;
import com.globits.security.dto.UserDto;

public class TeamUserDto extends BaseObjectDto {
	
	private UserDto user ;
	private TeamDto team;
	
	public TeamUserDto() {}
	public TeamUserDto(TeamUser entity) {
		this(entity, true);
	}
	public TeamUserDto(TeamUser entity,Boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			if(entity.getUser()!=null&&entity.getUser().getId()!=null) {
				this.user = new UserDto(entity.getUser());
			}
			if(entity.getTeam()!=null && entity.getTeam().getId()!=null&&check) {
				this.team = new TeamDto(entity.getTeam(),false);
			}
		}
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public TeamDto getTeam() {
		return team;
	}
	public void setTeam(TeamDto team) {
		this.team = team;
	}
	
	
}
