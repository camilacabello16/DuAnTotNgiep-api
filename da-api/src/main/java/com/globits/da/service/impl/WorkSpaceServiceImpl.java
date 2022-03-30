package com.globits.da.service.impl;

import java.util.HashSet;
import java.util.Iterator;
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
import com.globits.da.WorkSpaceConstants;
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.repository.WorkSpaceUserRepository;
import com.globits.da.service.WorkSpaceService;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class WorkSpaceServiceImpl extends GenericServiceImpl<WorkSpace, UUID> implements WorkSpaceService {

	@Autowired
	private WorkSpaceRepository workSpaceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkSpaceUserRepository workSpaceUserRepository;
	@Override
	public WorkSpaceDto saveOrUpdate(UUID id, WorkSpaceDto dto) {
		if(dto!=null) {
			WorkSpace entity = null;
			WorkSpace parent = null;
			if(id!=null) {
				entity = workSpaceRepository.getOne(id);
			} else {
				entity = new WorkSpace();
			}
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setType(dto.getType());
			entity.setVisibility(dto.getVisibility());
			if(dto.getParent()!=null&&dto.getParent().getId()!=null) {
				parent = workSpaceRepository.findById(dto.getParent().getId()).orElse(null);
			}
			entity.setParent(parent);
            entity = workSpaceRepository.save(entity);
            if(dto.getUserIdHost()!=null) {
            	User user = userRepository.getOne(dto.getUserIdHost());
            	WorkSpaceUser workSpaceUser = new WorkSpaceUser();
            	workSpaceUser.setUser(user);
            	workSpaceUser.setWorkSpace(entity);
            	workSpaceUser.setRole(WorkSpaceConstants.ROLE_WORKSPACE_MANAGER);
            	workSpaceUser.setStatus(1);
            	workSpaceUser = workSpaceUserRepository.save(workSpaceUser);
            }
            if(entity!=null) {
            	return new WorkSpaceDto(entity,true);
            }
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			workSpaceRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<WorkSpaceDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  WorkSpace as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.WorkSpaceDto(entity,true) from  WorkSpace as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, WorkSpaceDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<WorkSpaceDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public WorkSpaceDto getById(UUID id) {
		if(id!=null) {			
			WorkSpace entity = workSpaceRepository.getOne(id);
			if(entity!=null) {
				return new WorkSpaceDto(entity,true);
			}
			
		}
		return null;
	}

}
