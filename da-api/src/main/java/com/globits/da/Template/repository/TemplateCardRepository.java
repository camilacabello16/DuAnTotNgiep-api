package com.globits.da.Template.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.dto.WorkSpaceUserDto;

@Repository
public interface TemplateCardRepository extends JpaRepository<TemplateCard, UUID> {
	
	@Query("select entity FROM TemplateCard entity WHERE entity.templateWorkSpace.id =?1")
	List<TemplateCard> getTemplateCardByWorkSpaceId(UUID workSpaceId);
}
