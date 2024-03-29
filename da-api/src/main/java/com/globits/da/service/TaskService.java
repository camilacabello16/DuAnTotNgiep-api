package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Task;
import com.globits.da.dto.CardDto;
import com.globits.da.dto.TaskDto;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.dto.search.TaskSearchDto;

@Service
public interface TaskService extends GenericService<Task,UUID> {
	
	public TaskDto saveOrUpdate(UUID id,TaskDto dto);
	public Boolean deleteById(UUID id);
	Page<TaskDto> searchByPage(TaskSearchDto dto);
	TaskDto getById(UUID id);
	TaskDto updateViewIndex(UUID id,Integer viewIndex);
}
