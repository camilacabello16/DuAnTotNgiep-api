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

import com.globits.da.dto.TaskDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class RestTaskController {
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TaskDto> save(@RequestBody TaskDto dto) {
		TaskDto result = taskService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TaskDto> update(@RequestBody TaskDto dto,@PathVariable("id") UUID id) {
		TaskDto result = taskService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<TaskDto>> search(@RequestBody SearchDto dto) {
		Page<TaskDto> result = taskService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = taskService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
