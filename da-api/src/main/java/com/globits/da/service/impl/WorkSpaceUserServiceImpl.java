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
import com.globits.da.domain.Notification;
import com.globits.da.domain.ReceiverNotification;
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.da.dto.WorkSpaceUserDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.dto.search.WorkSpaceUserSearchDto;
import com.globits.da.repository.NotificationRepository;
import com.globits.da.repository.ReceiverNotificationRepository;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.repository.WorkSpaceUserRepository;
import com.globits.da.service.NotificationService;
import com.globits.da.service.WorkSpaceService;
import com.globits.da.service.WorkSpaceUserService;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class WorkSpaceUserServiceImpl extends GenericServiceImpl<WorkSpaceUser, UUID> implements WorkSpaceUserService {

	@Autowired
	private WorkSpaceRepository workSpaceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkSpaceUserRepository workSpaceUserRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ReceiverNotificationRepository receiverNotificationRepository;
	@Override
	public WorkSpaceUserDto saveOrUpdate(UUID id, WorkSpaceUserDto dto) {
		if(dto!=null) {
			WorkSpaceUser entity = null;
			WorkSpace workSpace = null;
			User user = null;
			if(id!=null) {
				entity = workSpaceUserRepository.getOne(id);
			} else {
				entity = new WorkSpaceUser();
			}
			entity.setRole(dto.getRole());
			entity.setStatus(dto.getStatus());
			if(dto.getWorkSpace()!=null&&dto.getWorkSpace().getId()!=null) {
				workSpace = workSpaceRepository.findById(dto.getWorkSpace().getId()).orElse(null);
			}
			entity.setWorkSpace(workSpace);
			if(dto.getUser()!=null&&dto.getUser().getId()!=null) {
				user = userRepository.findById(dto.getUser().getId()).orElse(null);
			}
			entity.setUser(user);
			entity = workSpaceUserRepository.save(entity);
			
            if(entity!=null) {
            	return new WorkSpaceUserDto(entity,true);
            }
		}
		return null;
	}

	@Override
	public Boolean deleteById(UUID id) {
		if(id!=null) {
			workSpaceUserRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Page<WorkSpaceUserDto> searchByPage(WorkSpaceUserSearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from  WorkSpaceUser as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.WorkSpaceUserDto(entity,true) from  WorkSpaceUser as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}
		if(dto.getUserId()!=null) {
			whereClause += " AND ( entity.user.id = :userId )";
		}
		if(dto.getRole()!=null) {
			whereClause += " AND ( entity.role = :role )";
		}
		if(dto.getStatus()!=null) {
			whereClause += " AND ( entity.status = :status )";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, WorkSpaceUserDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if (dto.getUserId() != null) {
			q.setParameter("userId", dto.getUserId());
			qCount.setParameter("userId", dto.getUserId());
		}
		if (dto.getRole() != null) {
			q.setParameter("role", dto.getRole());
			qCount.setParameter("role", dto.getRole());
		}
		if (dto.getStatus() != null) {
			q.setParameter("status", dto.getStatus());
			qCount.setParameter("status",dto.getStatus());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<WorkSpaceUserDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);		
		return new PageImpl<>(entities, pageable, count);
	}

	@Override
	public WorkSpaceUserDto getById(UUID id) {
		if(id!=null) {			
			WorkSpaceUser entity = workSpaceUserRepository.getOne(id);
			if(entity!=null) {
				return new WorkSpaceUserDto(entity,true);
			}
			
		}
		return null;
	}

	@Override
	public List<WorkSpaceUserDto> getAllByUserId(Long id) {
		if(id!=null) {
			List<WorkSpaceUserDto> result = workSpaceUserRepository.getWorkSpaceUserByUserId(id);
			if(result!=null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public Boolean inviteUser(UUID workSpaceId, String username) {
		if(workSpaceId!=null&&username!=null) {
			WorkSpace workSpace = workSpaceRepository.getOne(workSpaceId);
			User user = userRepository.findByUsername(username);
			WorkSpaceUser entity = new WorkSpaceUser();
			entity.setUser(user);
			entity.setWorkSpace(workSpace);
			entity.setStatus(0);
			entity.setRole(WorkSpaceConstants.ROLE_WORKSPACE_USER);
			entity = workSpaceUserRepository.save(entity);
			if(entity!=null) {
				ReceiverNotification receiverNotification = new ReceiverNotification();
				Notification notification = new Notification();
				notification.setType(1);
				notification.setContent(WorkSpaceConstants.CONTENT_INVITE + workSpace.getName());
				notification = notificationRepository.save(notification);
				if(notification!=null) {
					receiverNotification.setNotification(notification);
					receiverNotification.setUser(user);
					receiverNotification.setIsRead(0);
					receiverNotification = receiverNotificationRepository.save(receiverNotification);
				}

			}
			if(entity!=null) {
				return true;
			}					
		}
		return false;
	}

	@Override
	public Boolean updateStatus(UUID id, Boolean check) {
		if(check) {
			if(id!=null) {
				WorkSpaceUser workSpaceUser = workSpaceUserRepository.getOne(id);
				workSpaceUser.setStatus(1);
				workSpaceUser = workSpaceUserRepository.save(workSpaceUser);
				return true;
			}
		} else {
			if(id!=null) {
				workSpaceUserRepository.deleteById(id);
				return false;
			}
		}
		return false;
	}

	@Override
	public List<WorkSpaceUserDto> getWorkSpaceByUserId(Long id) {
		if(id!=null) {
			List<WorkSpaceUserDto> result = workSpaceUserRepository.getWorkSpaceUserByUserId(id);
			if(result!=null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<WorkSpaceUserDto> getWorkSpaceByRole(String role) {
		if(role!=null) {
			List<WorkSpaceUserDto> result = workSpaceUserRepository.getWorkSpaceUserByRole(role);
			if(result!=null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<WorkSpaceUserDto> getWorkSpaceByRoleAndUserId(String role, Long userId) {
		if(role!=null && userId!=null) {
			List<WorkSpaceUserDto> result = workSpaceUserRepository.getWorkSpaceUserByRoleAndUserID(role,userId);
			if(result!=null) {
				return result;
			}
		}
		return null;
	}

}
