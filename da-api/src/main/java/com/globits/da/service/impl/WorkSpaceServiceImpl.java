package com.globits.da.service.impl;

import java.util.ArrayList;
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
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.Template.dto.TemplateWorkSpaceDto;
import com.globits.da.Template.repository.TemplateCardRepository;
import com.globits.da.Template.repository.TemplateWorkSpaceRepository;
import com.globits.da.Template.service.TemplateWorkSpaceService;
import com.globits.da.domain.Card;
import com.globits.da.domain.Task;
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.WorkSpaceUserDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CardRepository;
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
	@Autowired
	private TemplateWorkSpaceRepository templateWorkSpaceRepository;
	@Autowired
	private TemplateWorkSpaceService templateWorkSpaceService;
	@Autowired
	private TemplateCardRepository templateCardRepository;
	@Autowired
	private CardRepository cardRepository;

	@Override
	public WorkSpaceDto saveOrUpdate(UUID id, WorkSpaceDto dto) {
		if (dto != null) {
			WorkSpace entity = null;
			WorkSpace parent = null;
			if (id != null) {
				entity = workSpaceRepository.getOne(id);
			} else {
				entity = new WorkSpace();
			}
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setType(dto.getType());
			entity.setVisibility(dto.getVisibility());
			if (dto.getParent() != null && dto.getParent().getId() != null) {
				parent = workSpaceRepository.findById(dto.getParent().getId()).orElse(null);
			}
			entity.setParent(parent);
			entity = workSpaceRepository.save(entity);
			if (dto.getUserIdHost() != null) {
				User user = userRepository.getOne(dto.getUserIdHost());
				WorkSpaceUser workSpaceUser = new WorkSpaceUser();
				workSpaceUser.setUser(user);
				workSpaceUser.setWorkSpace(entity);
				workSpaceUser.setRole(WorkSpaceConstants.ROLE_WORKSPACE_MANAGER);
				workSpaceUser.setStatus(1);
				workSpaceUser = workSpaceUserRepository.save(workSpaceUser);
			}
			if (entity != null) {
				return new WorkSpaceDto(entity, true);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
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
		if (id != null) {
			WorkSpace entity = workSpaceRepository.getOne(id);
			List<UserDto> userDtos = new ArrayList<UserDto>();
			List<String> roles = new ArrayList<String>();
			roles.add(WorkSpaceConstants.ROLE_WORKSPACE_MANAGER);
			roles.add(WorkSpaceConstants.ROLE_WORKSPACE_USER);
			List<WorkSpaceUserDto> workSpaceUserDtos = workSpaceUserRepository.getWorkSpaceUserByRole(roles);
			WorkSpaceDto result = new WorkSpaceDto(entity,true);
			for(WorkSpaceUserDto workSpaceUserDto:workSpaceUserDtos) {
				userDtos.add(workSpaceUserDto.getUser());
			}
			result.setUsers(userDtos);
			if(result!=null) {
				return result;
			}
			

		}
		return null;
	}

	@Override
	public WorkSpaceDto cloneTemplateWorkSpace(WorkSpaceDto dto) {
		if (dto != null) {
			TemplateWorkSpaceDto templateWorkSpaceDto = null;
			if (dto.getIdTemplateWorkSpace() != null) {
				templateWorkSpaceDto = templateWorkSpaceService.getById(dto.getIdTemplateWorkSpace());
			}
			WorkSpace workSpaceChild = new WorkSpace();
			WorkSpace parent = null;
			if (templateWorkSpaceDto != null && templateWorkSpaceDto.getId() != null) {
				workSpaceChild.setName(templateWorkSpaceDto.getName());
				workSpaceChild.setType(templateWorkSpaceDto.getType());
				workSpaceChild.setDescription(templateWorkSpaceDto.getDescription());
				workSpaceChild.setVisibility(templateWorkSpaceDto.getVisibility());
				if (dto != null && dto.getParent().getId() != null) {
					parent = workSpaceRepository.findById(dto.getParent().getId()).orElse(null);
				}
				workSpaceChild.setParent(parent);
				List<TemplateCard> templateCards = templateCardRepository
						.getTemplateCardByWorkSpaceId(templateWorkSpaceDto.getId());
				if (templateCards != null) {
					Set<Card> cards = new HashSet<Card>();
					if (templateCards != null && templateCards.size() > 0) {
						for (TemplateCard templateCard : templateCards) {
							Card card = new Card();
							Set<Task> tasks = new HashSet<Task>();
							card.setName(templateCard.getName());
							card.setStatus(templateCard.getStatus());
							card.setViewIndex(templateCard.getViewIndex());
							if (templateCard.getTemplateTasks() != null && templateCard.getTemplateTasks().size() > 0) {
								for (TemplateTask templateTask : templateCard.getTemplateTasks()) {
									Task task = new Task();
									task.setName(templateTask.getName());
									task.setStartDate(templateTask.getStartDate());
									task.setEndDate(templateTask.getEndDate());
									task.setViewIndex(templateTask.getViewIndex());
									task.setCard(card);
									tasks.add(task);
								}
							}
							card.setTasks(tasks);
							card.setWorkSpace(workSpaceChild);
							cards.add(card);
						}
					}
					workSpaceChild.setCards(cards);
				}
				
				workSpaceChild = workSpaceRepository.save(workSpaceChild);
				if (workSpaceChild != null) {
					return new WorkSpaceDto(workSpaceChild);
				}
			}
		}
		return null;

	}
}
