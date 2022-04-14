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

import com.globits.da.Template.dto.TemplateCardDto;
import com.globits.da.Template.service.TemplateCardService;
import com.globits.da.dto.search.SearchDto;


@RestController
@RequestMapping("/api/template/card")
public class RestTemplateCardController {
	@Autowired
	TemplateCardService templateCardService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TemplateCardDto> save(@RequestBody TemplateCardDto dto) {
		TemplateCardDto result = templateCardService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TemplateCardDto> update(@RequestBody TemplateCardDto dto,@PathVariable("id") UUID id) {
		TemplateCardDto result = templateCardService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<TemplateCardDto>> search(@RequestBody SearchDto dto) {
		Page<TemplateCardDto> result = templateCardService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = templateCardService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
//	@RequestMapping(value = "/update-view", method = RequestMethod.POST)
//	public ResponseEntity<TemplateCardDto> updateViewIndex(@RequestParam(name = "viewIndex") Integer viewIndex,@RequestParam(name = "id") UUID id) {
//		TemplateCardDto result = templateCardService.updateViewIndex(id,viewIndex);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
}
