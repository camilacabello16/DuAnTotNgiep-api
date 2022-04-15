package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.WorkSpace;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace,UUID> {
	
	@Query("select entity FROM WorkSpace entity WHERE entity.parent.id =?1")
	List<WorkSpace> getAllByParentId(UUID parentId);
}
