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

import com.globits.da.dto.CardDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CardService;
@RestController
@RequestMapping("/api/card")
public class RestCardController {
	@Autowired
	CardService cardService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CardDto> save(@RequestBody CardDto dto) {
		CardDto result = cardService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CardDto> update(@RequestBody CardDto dto,@PathVariable("id") UUID id) {
		CardDto result = cardService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<CardDto>> search(@RequestBody SearchDto dto) {
		Page<CardDto> result = cardService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = cardService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
