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
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.Template.dto.TemplateTaskDto;
import com.globits.da.Template.repository.TemplateCardRepository;
import com.globits.da.Template.repository.TemplateTaskRepository;
import com.globits.da.Template.service.TemplateTaskService;
import com.globits.da.dto.search.SearchDto;

@Service
public class TemplateTaskServiceImpl extends GenericServiceImpl<TemplateTask, UUID> implements TemplateTaskService {

	@Autowired
	private TemplateTaskRepository templateTaskRepository;

	@Autowired
	private TemplateCardRepository templateCardRepository;
	@Override
	public TemplateTaskDto saveOrUpdate(UUID id, TemplateTaskDto dto) {
		if(dto!=null) {
			TemplateTask entity = null;
			TemplateCard templateCard = null;
			if(id!=null) {
				entity = templateTaskRepository.getOne(id);
			} else {
				entity = new TemplateTask();
			}
			entity.setName(dto.getName());
			entity.setViewIndex(dto.getViewIndex());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			if(dto.getTemplateCard()!=null&&dto.getTemplateCard().getId()!=null) {
				templateCard = templateCardRepository.findById(dto.getTemplateCard().getId()).orElse(null);
			}
			entity.setTemplateCard(templateCard);
            entity = templateTaskRepository.save(entity);
           
            if(entity!=null) {
            	return new TemplateTaskDto(entity,true);
            }
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			templateTaskRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<TemplateTaskDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  TemplateTask as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.TemplateTaskDto(entity,true) from  TemplateTask as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TemplateTaskDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TemplateTaskDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public TemplateTaskDto getById(UUID id) {
		if(id!=null) {			
			TemplateTask entity = templateTaskRepository.getOne(id);
			if(entity!=null) {
				return new TemplateTaskDto(entity,true);
			}
			
		}
		return null;
	}

}

