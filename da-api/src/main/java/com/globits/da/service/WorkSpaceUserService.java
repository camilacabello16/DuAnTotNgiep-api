package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.globits.core.service.GenericService;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.da.dto.WorkSpaceUserDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface WorkSpaceUserService  extends GenericService<WorkSpaceUser,UUID>{
	
	WorkSpaceUserDto saveOrUpdate(UUID id,WorkSpaceUserDto dto);
	Boolean deleteById(UUID id);
	Page<WorkSpaceUserDto> searchByPage(SearchDto dto);
	WorkSpaceUserDto getById(UUID id);
	List<WorkSpaceUserDto> getAllByUserId(Long id);
	Boolean inviteUser(UUID workSpaceId,String username);
	Boolean updateStatus(UUID id,Boolean status);// true : dong y, false: khong dong y
	
}
