package com.globits.da.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.da.domain.Card;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CardRepository;
import com.globits.da.repository.TaskRepository;
import com.globits.da.repository.WorkSpaceRepository;
import com.globits.da.service.CardService;
import com.globits.da.service.DashboardService;
import com.globits.da.service.TaskService;
import com.globits.da.service.WorkSpaceService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private WorkSpaceRepository workSpaceRepository;

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Map<String, Map<String, Long>> dashBoardChildWS(UUID parentId) {
		if (parentId != null) {
			Map<String, Map<String, Long>> result = new HashedMap<>();
			List<WorkSpace> workSpaces = workSpaceRepository.getAllByParentId(parentId);
			if (workSpaces != null && workSpaces.size() > 0) {
				for (WorkSpace workSpace2 : workSpaces) {
					if (workSpace2 != null) {
						Map<String, Long> workSpace = new HashedMap<String, Long>();
						List<Card> cards = cardRepository.getAllByWorkSpaceId(workSpace2.getId());
						for (Card card : cards) {
							if (card != null && card.getId() != null) {
								workSpace.put(card.getName(), taskRepository.totalTaskByCard(card.getId()));
							}
						}
						result.put(workSpace2.getName(), workSpace);
					}
				}
			}
			return result;
		}
		return null;
	}

}
