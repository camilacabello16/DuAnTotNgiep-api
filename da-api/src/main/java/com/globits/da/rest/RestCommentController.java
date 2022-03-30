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

import com.globits.da.dto.CommentDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CommentService;
@RestController
@RequestMapping("/api/comment")
public class RestCommentController {
	@Autowired
	CommentService CommentService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommentDto> save(@RequestBody CommentDto dto) {
		CommentDto result = CommentService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CommentDto> update(@RequestBody CommentDto dto,@PathVariable("id") UUID id) {
		CommentDto result = CommentService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<Page<CommentDto>> search(@RequestBody SearchDto dto) {
		Page<CommentDto> result = CommentService.searchByPage(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = CommentService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
