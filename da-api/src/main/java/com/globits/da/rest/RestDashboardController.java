package com.globits.da.rest;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.CardDto;
import com.globits.da.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class RestDashboardController {
	
	@Autowired
	DashboardService dashboardService;
	
	@RequestMapping(value = "/task/{workSpaceId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Map<String, Long>>> reportTaskByWorkspaceId(@PathVariable(name = "workSpaceId") UUID workSpaceId) {
		Map<String, Map<String, Long>> result = dashboardService.dashBoardChildWS(workSpaceId);
		System.out.print(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/member/{workSpaceId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Long>> reportMemberByWorkspaceId(@PathVariable(name = "workSpaceId") UUID workSpaceId) {
		Map<String,Long> result = dashboardService.dashBoardTaskOfMember(workSpaceId);
		System.out.print(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
