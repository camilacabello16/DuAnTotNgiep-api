package com.globits.da.Template.service.impl;

import java.util.List;
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
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.Template.dto.TemplateWorkSpaceDto;
import com.globits.da.Template.dto.search.TemplateWorkSpaceSearchDto;
import com.globits.da.Template.service.TemplateWorkSpaceService;
import com.globits.da.dto.search.SearchDto;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.da.Template.repository.*;


@Service
public class TemplateWorkSpaceServiceImpl extends GenericServiceImpl<TemplateWorkSpace, UUID> implements TemplateWorkSpaceService {

	@Autowired
	private TemplateWorkSpaceRepository templateWorkSpaceRepository;

	@Autowired
	private UserRepository userRepository;
	@Override
	public TemplateWorkSpaceDto saveOrUpdate(UUID id, TemplateWorkSpaceDto dto) {
		if(dto!=null) {
			TemplateWorkSpace entity = null;
			TemplateWorkSpace parent = null;
			User user = null;
			if(id!=null) {
				entity = templateWorkSpaceRepository.getOne(id);
			} else {
				entity = new TemplateWorkSpace();
			}
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setType(dto.getType());
			entity.setVisibility(dto.getVisibility());
			if(dto.getParent()!=null&&dto.getParent().getId()!=null) {
				parent = templateWorkSpaceRepository.findById(dto.getParent().getId()).orElse(null);
			}
			if(dto.getUserIdHost()!=null) {
				user = userRepository.findById(dto.getUserIdHost()).orElse(null);
			}
			entity.setUser(user);
			entity.setParent(parent);
            entity = templateWorkSpaceRepository.save(entity);
           
            if(entity!=null) {
            	return new TemplateWorkSpaceDto(entity,true);
            }
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			templateWorkSpaceRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<TemplateWorkSpaceDto> searchByPage(TemplateWorkSpaceSearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  TemplateWorkSpace as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.TemplateWorkSpaceDto(entity,true) from  TemplateWorkSpace as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}
		if(dto.getUserId()!=null) {
			whereClause += " AND ( entity.user.id =:userId )";
		}
		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TemplateWorkSpaceDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if(dto.getUserId()!=null) {
			q.setParameter("userId", dto.getUserId());
			qCount.setParameter("userId", dto.getUserId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TemplateWorkSpaceDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public TemplateWorkSpaceDto getById(UUID id) {
		if(id!=null) {			
			TemplateWorkSpace entity = templateWorkSpaceRepository.findById(id).orElse(null);
			if(entity!=null) {
				return new TemplateWorkSpaceDto(entity,true);
			}
			
		}
		return null;
	}

}

