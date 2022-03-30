package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.globits.core.service.GenericService;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface WorkSpaceService  extends GenericService<WorkSpace,UUID>{
	
	WorkSpaceDto saveOrUpdate(UUID id,WorkSpaceDto dto);
	Boolean deleteById(UUID id);
	Page<WorkSpaceDto> searchByPage(SearchDto dto);
	WorkSpaceDto getById(UUID id);
}
