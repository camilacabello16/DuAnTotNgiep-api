package com.globits.da.Template.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.dto.TemplateCardDto;
import com.globits.da.dto.search.SearchDto;

public interface TemplateCardService extends GenericService<TemplateCard, UUID> {
	TemplateCardDto saveOrUpdate(UUID id,TemplateCardDto dto);
	Boolean deleteById(UUID id);
	Page<TemplateCardDto> searchByPage(SearchDto dto);
	TemplateCardDto getById(UUID id);
}
