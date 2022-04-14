package com.globits.da.Template.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.Template.domain.TemplateTask;
import com.globits.da.Template.dto.TemplateTaskDto;
import com.globits.da.dto.search.SearchDto;

public interface TemplateTaskService extends GenericService<TemplateTask, UUID> {
	
	TemplateTaskDto saveOrUpdate(UUID id, TemplateTaskDto dto);

	Boolean deleteById(UUID id);

	Page<TemplateTaskDto> searchByPage(SearchDto dto);

	TemplateTaskDto getById(UUID id);

}
