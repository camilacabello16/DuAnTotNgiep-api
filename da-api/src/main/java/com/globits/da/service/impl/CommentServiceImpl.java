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
import com.globits.da.domain.Comment;
import com.globits.da.domain.Task;
import com.globits.da.domain.Comment;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.repository.CommentRepository;
import com.globits.da.repository.TaskRepository;
import com.globits.da.service.CommentService;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;

import ch.qos.logback.core.joran.conditional.IfAction;

import com.globits.da.service.CommentService;

@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment, UUID> implements CommentService {

	
	@Autowired
	private CommentRepository CommentRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public CommentDto saveOrUpdate(UUID id, CommentDto dto) {
		if(dto!=null) {
			Comment entity = null;
			Task task = null;
			User user = null;
			if(id!=null) {
				entity = CommentRepository.getOne(id);
			}
			if(entity==null) {
				entity = new Comment();
			}
			entity.setContent(dto.getContent());
			if(dto.getTask()!=null&&dto.getTask().getId()!=null) {
				task = taskRepository.findById(dto.getTask().getId()).orElse(null);
			}
			entity.setTask(task);
			if(dto.getUser()!=null&&dto.getUser().getId()!=null) {
				user = userRepository.findById(dto.getUser().getId()).orElse(null);
			}
			entity.setUser(user);
			entity = CommentRepository.save(entity);
			if(entity!=null) {
				return new CommentDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			CommentRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<CommentDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  Comment as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.CommentDto(entity) from  Comment as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text)";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CommentDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CommentDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public CommentDto getById(UUID id) {
		if(id!=null) {			
			Comment entity = CommentRepository.getOne(id);
			if(entity!=null) {
				return new CommentDto(entity);
			}
			
		}
		return null;
	}

}
