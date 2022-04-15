package com.globits.da.Template.service.impl;

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
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.Template.dto.TemplateCardDto;
import com.globits.da.Template.dto.TemplateTaskDto;
import com.globits.da.Template.dto.TemplateWorkSpaceDto;
import com.globits.da.Template.dto.search.TemplateWorkSpaceSearchDto;
import com.globits.da.Template.service.TemplateWorkSpaceService;
import com.globits.da.domain.Card;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.search.SearchDto;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.da.Template.repository.*;

@Service
public class TemplateWorkSpaceServiceImpl extends GenericServiceImpl<TemplateWorkSpace, UUID>
		implements TemplateWorkSpaceService {

	@Autowired
	private TemplateWorkSpaceRepository templateWorkSpaceRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TemplateCardRepository templateCardRepository;
	@Autowired
	private TemplateTaskRepository templateTaskRepository;

	@Override
	public TemplateWorkSpaceDto saveOrUpdate(UUID id, TemplateWorkSpaceDto dto) {
		if (dto != null) {
			TemplateWorkSpace entity = null;
			User user = null;
			if (id != null) {
				entity = templateWorkSpaceRepository.getOne(id);
			} else {
				entity = new TemplateWorkSpace();
			}
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setType(dto.getType());
			entity.setVisibility(dto.getVisibility());
			if (dto.getUserIdHost() != null) {
				user = userRepository.findById(dto.getUserIdHost()).orElse(null);
			}
			entity.setUser(user);

			if (dto.getCards() != null && dto.getCards().size() > 0) {
				Set<TemplateCard> templateCards = new HashSet<TemplateCard>();
				for (TemplateCardDto templateCardDto : dto.getCards()) {
					TemplateCard templateCard = null;
					if (templateCardDto != null && templateCardDto.getId() != null) {
						templateCard = templateCardRepository.getOne(templateCardDto.getId());
					} else {
						templateCard = new TemplateCard();
					}
					templateCard.setName(templateCardDto.getName());
					templateCard.setStatus(templateCardDto.getStatus());
					templateCard.setViewIndex(templateCardDto.getViewIndex());
					templateCard.setTemplateWorkSpace(entity);

					if (templateCardDto.getTasks() != null && templateCardDto.getTasks().size() > 0) {
						Set<TemplateTask> templateTasks = new HashSet<TemplateTask>();
						for (TemplateTaskDto taskDto : templateCardDto.getTasks()) {
							TemplateTask templateTask = null;
							if (taskDto != null && taskDto.getId() != null) {
								templateTask = templateTaskRepository.getOne(taskDto.getId());
							} else {
								templateTask = new TemplateTask();
							}
							templateTask.setName(taskDto.getName());
							templateTask.setStartDate(taskDto.getStartDate());
							templateTask.setEndDate(taskDto.getEndDate());
							templateTask.setViewIndex(taskDto.getViewIndex());
							templateTask.setTemplateCard(templateCard);
							templateTasks.add(templateTask);
						}
						if (templateCard.getTemplateTasks() != null) {
							templateCard.getTemplateTasks().clear();
							templateCard.getTemplateTasks().addAll(templateTasks);
						} else {
							templateCard.setTemplateTasks(templateTasks);
						}

					} else {
						if (templateCard.getTemplateTasks() != null) {
							templateCard.getTemplateTasks().clear();
						}
					}
					templateCards.add(templateCard);
				}
				if (entity.getTemplateCards() != null) {
					entity.getTemplateCards().clear();
					entity.getTemplateCards().addAll(templateCards);
				} else {
					entity.setTemplateCards(templateCards);
				}
			} else {
				if (entity.getTemplateCards() != null) {
					entity.getTemplateCards().clear();
				}
			}

			entity = templateWorkSpaceRepository.save(entity);
			if (entity != null) {
				return new TemplateWorkSpaceDto(entity, true);
			}

		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
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
		if (dto.getUserId() != null) {
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
		if (dto.getUserId() != null) {
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
		if (id != null) {
			TemplateWorkSpace entity = templateWorkSpaceRepository.findById(id).orElse(null);
			if (entity != null) {
				return new TemplateWorkSpaceDto(entity, true);
			}

		}
		return null;
	}

}
