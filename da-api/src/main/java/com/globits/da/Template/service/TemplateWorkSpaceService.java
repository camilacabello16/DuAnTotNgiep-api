package com.globits.da.Template.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.Template.dto.*;
import com.globits.da.Template.dto.search.TemplateWorkSpaceSearchDto;
import com.globits.da.dto.search.SearchDto;

public interface TemplateWorkSpaceService extends GenericService<TemplateWorkSpace, UUID>{
	TemplateWorkSpaceDto saveOrUpdate(UUID id,TemplateWorkSpaceDto dto);
	Boolean deleteById(UUID id);
	Page<TemplateWorkSpaceDto> searchByPage(TemplateWorkSpaceSearchDto dto);
	TemplateWorkSpaceDto getById(UUID id);
}
