package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.da.dto.WorkSpaceUserDto;

@Repository
public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser,UUID> {
	
	@Query("select new com.globits.da.dto.WorkSpaceUserDto(a) FROM WorkSpaceUser a WHERE a.user.id =:userId AND status = 1")
	List<WorkSpaceUserDto> getWorkSpaceUserByUserId( @Param("userId") Long userId);
	@Query("select new com.globits.da.dto.WorkSpaceUserDto(a) FROM WorkSpaceUser a WHERE a.role in (:role) AND status = 1")
	List<WorkSpaceUserDto> getWorkSpaceUserByRole( @Param("role") List<String> role);
	@Query("select new com.globits.da.dto.WorkSpaceUserDto(a) FROM WorkSpaceUser a WHERE a.role=:role AND a.user.id =:userId AND status = 1")
	List<WorkSpaceUserDto> getWorkSpaceUserByRoleAndUserID( @Param("role") String role, @Param("userId") Long userId);
}
