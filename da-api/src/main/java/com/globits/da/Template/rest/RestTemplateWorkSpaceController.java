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

import com.globits.da.Template.dto.TemplateWorkSpaceDto;
import com.globits.da.Template.dto.search.TemplateWorkSpaceSearchDto;
import com.globits.da.Template.service.TemplateWorkSpaceService;
import com.globits.da.dto.search.SearchDto;


@RestController
@RequestMapping("/api/template/workspace")
public class RestTemplateWorkSpaceController {
	@Autowired
	TemplateWorkSpaceService templateWorkSpaceService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TemplateWorkSpaceDto> save(@RequestBody TemplateWorkSpaceDto dto) {
		TemplateWorkSpaceDto result = templateWorkSpaceService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TemplateWorkSpaceDto> update(@RequestBody TemplateWorkSpaceDto dto,@PathVariable("id") UUID id) {
		TemplateWorkSpaceDto result = templateWorkSpaceService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<TemplateWorkSpaceDto>> search(@RequestBody TemplateWorkSpaceSearchDto dto) {
		Page<TemplateWorkSpaceDto> result = templateWorkSpaceService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = templateWorkSpaceService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
//	@RequestMapping(value = "/update-view", method = RequestMethod.POST)
//	public ResponseEntity<TemplateWorkSpaceDto> updateViewIndex(@RequestParam(name = "viewIndex") Integer viewIndex,@RequestParam(name = "id") UUID id) {
//		TemplateWorkSpaceDto result = TemplateWorkSpaceService.updateViewIndex(id,viewIndex);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
}
