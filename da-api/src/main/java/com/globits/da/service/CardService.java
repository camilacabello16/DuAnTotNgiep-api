package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Card;
import com.globits.da.dto.CardDto;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface CardService extends GenericService<Card,UUID> {

	public CardDto saveOrUpdate(UUID id,CardDto dto);
	public Boolean deleteById(UUID id);
	Page<CardDto> searchByPage(SearchDto dto);
	CardDto getById(UUID id);
	CardDto updateViewIndex(UUID id,Integer viewIndex);

}
