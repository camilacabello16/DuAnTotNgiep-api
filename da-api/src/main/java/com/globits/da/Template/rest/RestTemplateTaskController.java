package com.globits.da.Template.rest;

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

import com.globits.da.Template.dto.TemplateTaskDto;
import com.globits.da.Template.service.TemplateTaskService;
import com.globits.da.dto.search.SearchDto;


@RestController
@RequestMapping("/api/template/task")
public class RestTemplateTaskController {
	@Autowired
	TemplateTaskService templateTaskService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TemplateTaskDto> save(@RequestBody TemplateTaskDto dto) {
		TemplateTaskDto result = templateTaskService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TemplateTaskDto> update(@RequestBody TemplateTaskDto dto,@PathVariable("id") UUID id) {
		TemplateTaskDto result = templateTaskService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<TemplateTaskDto>> search(@RequestBody SearchDto dto) {
		Page<TemplateTaskDto> result = templateTaskService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = templateTaskService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
//	@RequestMapping(value = "/update-view", method = RequestMethod.POST)
//	public ResponseEntity<TemplateTaskDto> updateViewIndex(@RequestParam(name = "viewIndex") Integer viewIndex,@RequestParam(name = "id") UUID id) {
//		TemplateTaskDto result = TemplateTaskService.updateViewIndex(id,viewIndex);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
}
