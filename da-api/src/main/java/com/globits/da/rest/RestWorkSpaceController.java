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
import org.springframework.web.bind.annotation.RestController;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.WorkSpaceService;

@RestController
@RequestMapping("/api/workspace")
public class RestWorkSpaceController {
	
	@Autowired
	WorkSpaceService workSpaceService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<WorkSpaceDto> save(@RequestBody WorkSpaceDto dto) {
		WorkSpaceDto result = workSpaceService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/clone-template" ,method = RequestMethod.POST)
	public ResponseEntity<WorkSpaceDto> cloneTemplate(@RequestBody WorkSpaceDto dto) {
		WorkSpaceDto result = workSpaceService.cloneTemplateWorkSpace(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<WorkSpaceDto> update(@RequestBody WorkSpaceDto dto,@PathVariable("id") UUID id) {
		WorkSpaceDto result = workSpaceService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<WorkSpaceDto>> search(@RequestBody SearchDto dto) {
		Page<WorkSpaceDto> result = workSpaceService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = workSpaceService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<WorkSpaceDto> getById(@PathVariable("id") UUID id) {
		WorkSpaceDto result = workSpaceService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
