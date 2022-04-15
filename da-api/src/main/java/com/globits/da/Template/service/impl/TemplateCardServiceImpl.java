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
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.Template.dto.TemplateCardDto;
import com.globits.da.Template.repository.TemplateCardRepository;
import com.globits.da.Template.repository.TemplateWorkSpaceRepository;
import com.globits.da.Template.service.TemplateCardService;
import com.globits.da.dto.search.SearchDto;

@Service
public class TemplateCardServiceImpl extends GenericServiceImpl<TemplateCard, UUID> implements TemplateCardService {

	@Autowired
	private TemplateCardRepository templateCardRepository;

	@Autowired
	private TemplateWorkSpaceRepository templateWorkSpaceRepository;
	@Override
	public TemplateCardDto saveOrUpdate(UUID id, TemplateCardDto dto) {
		if(dto!=null) {
			TemplateCard entity = null;
			TemplateWorkSpace templateWorkSpace = null;
			if(id!=null) {
				entity = templateCardRepository.getOne(id);
			} else {
				entity = new TemplateCard();
			}
			entity.setName(dto.getName());
			entity.setStatus(dto.getStatus());
			entity.setViewIndex(dto.getViewIndex());
			
			if(dto.getWorkSpace()!=null&&dto.getWorkSpace().getId()!=null) {
				templateWorkSpace = templateWorkSpaceRepository.findById(dto.getWorkSpace().getId()).orElse(null);
			}
			entity.setTemplateWorkSpace(templateWorkSpace);
            entity = templateCardRepository.save(entity);
           
            if(entity!=null) {
            	return new TemplateCardDto(entity);
            }
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			templateCardRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<TemplateCardDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  TemplateCard as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.TemplateCardDto(entity) from  TemplateCard as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TemplateCardDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TemplateCardDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public TemplateCardDto getById(UUID id) {
		if(id!=null) {			
			TemplateCard entity = templateCardRepository.getOne(id);
			if(entity!=null) {
				return new TemplateCardDto(entity);
			}
			
		}
		return null;
	}

}

