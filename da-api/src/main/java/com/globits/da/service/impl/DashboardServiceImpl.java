package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.da.WorkSpaceConstants;
import com.globits.da.domain.Card;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.WorkSpaceUserDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CardRepository;
import com.globits.da.repository.TaskRepository;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.repository.WorkSpaceUserRepository;
import com.globits.da.service.CardService;
import com.globits.da.service.DashboardService;
import com.globits.da.service.TaskService;
import com.globits.da.service.WorkSpaceService;
import com.globits.security.dto.UserDto;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private WorkSpaceRepository workSpaceRepository;

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private WorkSpaceUserRepository workSpaceUserRepository;
	@Autowired
	private WorkSpaceService workSpaceService;

	@Override
	public Map<String, Long> dashBoardChildWS(UUID childWorkSpaceId) {
		if (childWorkSpaceId != null) {
			Map<String, Long> result = new HashedMap<>();
			List<Card> cards = cardRepository.getAllByWorkSpaceId(childWorkSpaceId);
			for (Card card : cards) {
				if (card != null && card.getId() != null) {
					result.put(card.getName(), taskRepository.totalTaskByCard(card.getId()));
				}
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public Map<String, Long> dashBoardTaskOfMember(UUID childId) {
		if (childId != null) {
			Map<String, Long> result = new HashedMap<String, Long>();
			List<String> roles = new ArrayList<String>();
			roles.add(WorkSpaceConstants.ROLE_WORKSPACE_MANAGER);
			roles.add(WorkSpaceConstants.ROLE_WORKSPACE_USER);
			WorkSpaceDto workSpaceDto = workSpaceService.getById(childId);
			if (workSpaceDto != null) {
				if (workSpaceDto.getUsers() != null && workSpaceDto.getUsers().size() > 0) {
					for (UserDto userDto : workSpaceDto.getUsers()) {
						result.put(userDto.getUsername(), taskRepository.totalTaskByUserId(userDto.getId()));
					}
				}
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
