package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Team;
import com.globits.da.dto.TeamDto;
import com.globits.da.dto.search.SearchDto;

public interface TeamService  extends GenericService<Team, UUID>{
		
	public TeamDto saveOrUpdate(UUID id,TeamDto dto);
	public Boolean deleteById(UUID id);
	Page<TeamDto> searchByPage(SearchDto dto);
	TeamDto getById(UUID id);
}
