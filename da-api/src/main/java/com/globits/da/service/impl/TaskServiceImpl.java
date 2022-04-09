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
import com.globits.da.domain.Card;
import com.globits.da.domain.Task;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.CardDto;
import com.globits.da.dto.TaskDto;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CardRepository;
import com.globits.da.repository.TaskRepository;
import com.globits.da.service.TaskService;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task, UUID> implements TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	CardRepository cardRepository;
	@Override
	public TaskDto saveOrUpdate(UUID id, TaskDto dto) {
		if(dto!=null) {
			Task entity = null;
			Card card = null;
			if(id!=null) {
				entity = taskRepository.getOne(id);
			}
			if(entity==null) {
				entity = new Task();
			}
			entity.setName(dto.getName());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setViewIndex(dto.getViewIndex());
			if(dto.getCard()!=null&&dto.getCard().getId()!=null) {
				card = cardRepository.findById(dto.getCard().getId()).orElse(null);
			}
			entity.setCard(card);
			entity = taskRepository.save(entity);
			if(entity!=null) {
				return new TaskDto(entity,true);
			}
			
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			taskRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<TaskDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  Task as entity join Card c on entity.card.id = c.id join "
				+ " WorkSpace w on w.id = c.workSpace.id where (1=1)   ";
		String sql = "select new com.globits.da.dto.TaskDto(entity,true) from  Task as entity join Card c on entity.card.id = c.id join"
				+ " WorkSpace w on w.id = c.workSpace.id where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}
		if(dto.getCardId()!=null) {
			whereClause += " AND ( c.id =:cardId)";
		}
		if(dto.getWorkSpaceId()!=null) {
			whereClause += " AND ( w.id =:workSpaceId)";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, TaskDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if (dto.getCardId() != null) {
			q.setParameter("cardId", dto.getCardId() );
			qCount.setParameter("cardId", dto.getCardId());
		}
		if (dto.getWorkSpaceId() != null) {
			q.setParameter("workSpaceId", dto.getWorkSpaceId() );
			qCount.setParameter("workSpaceId", dto.getWorkSpaceId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<TaskDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public TaskDto getById(UUID id) {
		if(id!=null) {			
			Task entity = taskRepository.getOne(id);
			if(entity!=null) {
				return new TaskDto(entity,true);
			}
			
		}
		return null;
	}

	@Override
	public TaskDto updateViewIndex(UUID id, Integer viewIndex) {
		if(id!=null&&viewIndex!=null) {
			Task task = taskRepository.getOne(id);
			if (task!=null) {
				task.setViewIndex(viewIndex);
			}
			task = taskRepository.save(task);
			return new TaskDto(task,true);
		}
		return null;
	}

}
