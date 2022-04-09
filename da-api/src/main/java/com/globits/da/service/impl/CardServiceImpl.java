package com.globits.da.service.impl;

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
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.Card;
import com.globits.da.dto.CardDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.repository.CardRepository;
import com.globits.da.service.CardService;

@Service
public class CardServiceImpl extends GenericServiceImpl<Card, UUID> implements CardService {

	
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private WorkSpaceRepository workSpaceRepository;
	
	@Override
	public CardDto saveOrUpdate(UUID id, CardDto dto) {
		if(dto!=null) {
			Card entity = null;
			WorkSpace workSpace = null;
			if(id!=null) {
				entity = cardRepository.getOne(id);
			}
			if(entity==null) {
				entity = new Card();
			}
			entity.setName(dto.getName());
			entity.setStatus(dto.getStatus());
			entity.setViewIndex(dto.getViewIndex());
			if(dto.getWorkSpace()!=null&&dto.getWorkSpace().getId()!=null) {
				workSpace = workSpaceRepository.findById(dto.getWorkSpace().getId()).orElse(null);
			}
			entity.setWorkSpace(workSpace);
			entity = cardRepository.save(entity);
			if(entity!=null) {
				return new CardDto(entity,true);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			cardRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<CardDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  Card as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.CardDto(entity,true) from  Card as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}
		if(dto.getWorkSpaceId()!=null) {
			whereClause += " AND ( entity.workSpace.id =:id)";
		}
		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CardDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if (dto.getWorkSpaceId() != null) {
			q.setParameter("id", dto.getWorkSpaceId() );
			qCount.setParameter("id", dto.getWorkSpaceId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CardDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public CardDto getById(UUID id) {
		if(id!=null) {			
			Card entity = cardRepository.getOne(id);
			if(entity!=null) {
				return new CardDto(entity,true);
			}
			
		}
		return null;
	}

	@Override
	public CardDto updateViewIndex(UUID id, Integer viewIndex) {
		if(id!=null&&viewIndex!=null) {
			Card card = cardRepository.getOne(id);
			if (card!=null) {
				card.setViewIndex(viewIndex);
			}
			card = cardRepository.save(card);
			return new CardDto(card,true);
		}
		return null;
	}

}
