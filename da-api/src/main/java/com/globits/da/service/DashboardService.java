package com.globits.da.service;

import java.util.Map;
import java.util.UUID;

public interface DashboardService {
	
	Map<String, Long> dashBoardChildWS(UUID parentId);
	Map<String, Long> dashBoardTaskOfMember(UUID childId);
}
