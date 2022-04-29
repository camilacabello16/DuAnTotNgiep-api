package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Team;
import com.globits.da.domain.TeamUser;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.TeamDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.dto.search.TeamSearchDto;
import com.globits.da.repository.TeamRepository;
import com.globits.da.repository.TeamUserRepository;
import com.globits.da.service.TeamService;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;

@Service
public class TeamServiceImpl extends GenericServiceImpl<Team, UUID> implements TeamService{
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamUserRepository teamUserRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public TeamDto saveOrUpdate(UUID id, TeamDto dto) {
		if(dto!=null) {
			Team entity = null;
			Set<TeamUser> teamUsers = new HashSet<TeamUser>();
			if(id!=null) {
				entity = teamRepository.getOne(id);
			}
			if(entity==null) {
				entity = new Team();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setDescription(dto.getDescription());
			entity.setHostId(dto.getHostId());
			if(dto.getUsers()!=null&&dto.getUsers().size()>0) {
				TeamUser hostUser = new TeamUser();
				if(dto.getHostId()!=null) {
					User user = userRepository.findById(dto.getHostId()).orElse(null);
					hostUser.setUser(user);
					hostUser.setTeam(entity);
					teamUsers.add(hostUser);
				}
				for(UserDto userDto:dto.getUsers()) {
					TeamUser teamUser = new TeamUser();
					User user = null;
					if(userDto!=null) {
						user = userRepository.findById(userDto.getId()).orElse(null);
					}
					teamUser.setUser(user);
					teamUser.setTeam(entity);
					teamUsers.add(teamUser);
					
				}
				if (entity.getTeamUsers() != null) {
					entity.getTeamUsers().clear();
					entity.getTeamUsers().addAll(teamUsers);
				} else {
					entity.setTeamUsers(teamUsers);
				}
			}
			entity = teamRepository.save(entity);
			if(entity!=null) {
				return new TeamDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			teamRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<TeamDto> searchByPage(TeamSearchDto dto) {
		if (dto == null) {
			return null;
		}

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		
		String orderBy = " ORDER BY entity.createDate DESC";
		
		String sqlCount = "select count(distinct entity.id) from  Team as entity join TeamUser tu on tu.team.id = entity.id where (1=1)   ";
		String sql = "select distinct new com.globits.da.dto.TeamDto(entity,true) from  Team as entity join TeamUser tu on tu.team.id = entity.id where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}
		if(dto.getUserId()!=null) {
			whereClause+= " AND tu.user.id = :userId ";
		}
		if(dto.getHostId()!=null) {
			whereClause+= " AND entity.hostId = :hostId ";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TeamDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if (dto.getUserId() != null) {
			q.setParameter("userId", dto.getUserId() );
			qCount.setParameter("userId", dto.getUserId());
		}
		if (dto.getHostId() != null) {
			q.setParameter("hostId", dto.getHostId() );
			qCount.setParameter("hostId", dto.getHostId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TeamDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public TeamDto getById(UUID id) {
		if(id!=null) {			
			Team entity = teamRepository.getOne(id);
			if(entity!=null) {
				return new TeamDto(entity);
			}
			
		}
		return null;
	}
}
