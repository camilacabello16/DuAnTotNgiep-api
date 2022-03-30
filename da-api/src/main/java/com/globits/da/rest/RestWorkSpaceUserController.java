package com.globits.da.rest;


import java.util.List;
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
import com.globits.da.dto.WorkSpaceUserDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.WorkSpaceUserService;

@RestController
@RequestMapping("/api/workspace-user")
public class RestWorkSpaceUserController {
	
	@Autowired
	WorkSpaceUserService workSpaceUserService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<WorkSpaceUserDto> save(@RequestBody WorkSpaceUserDto dto) {
		WorkSpaceUserDto result = workSpaceUserService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<WorkSpaceUserDto> update(@RequestBody WorkSpaceUserDto dto,@PathVariable("id") UUID id) {
		WorkSpaceUserDto result = workSpaceUserService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<WorkSpaceUserDto>> search(@RequestBody SearchDto dto) {
		Page<WorkSpaceUserDto> result = workSpaceUserService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = workSpaceUserService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<WorkSpaceUserDto>> getAllByUserId(@PathVariable("userId") Long id) {
		List<WorkSpaceUserDto> result = workSpaceUserService.getAllByUserId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{workspace-id}/{username}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> inviteUser(@PathVariable("workspace-id") UUID workId,@PathVariable("username") String username) {
		Boolean result = workSpaceUserService.inviteUser(workId, username);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/invite/{workspace-user-id}/{status}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> updateStatus(@PathVariable("workspace-user-id") UUID id,@PathVariable("status") Boolean status) {
		Boolean result = workSpaceUserService.updateStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
