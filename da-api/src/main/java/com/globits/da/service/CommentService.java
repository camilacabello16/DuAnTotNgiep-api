package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Comment;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface CommentService extends GenericService<Comment,UUID> {

	public CommentDto saveOrUpdate(UUID id,CommentDto dto);
	public Boolean deleteById(UUID id);
	Page<CommentDto> searchByPage(SearchDto dto);
	CommentDto getById(UUID id);

}
