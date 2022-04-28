package com.globits.da.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.TeamDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.TeamService;
@RestController
@RequestMapping("/api/team")
public class RestTeamController {
	@Autowired
	TeamService teamService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TeamDto> save(@RequestBody TeamDto dto) {
		TeamDto result = teamService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TeamDto> update(@RequestBody TeamDto dto,@PathVariable("id") UUID id) {
		TeamDto result = teamService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<TeamDto>> search(@RequestBody SearchDto dto) {
		Page<TeamDto> result = teamService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = teamService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
