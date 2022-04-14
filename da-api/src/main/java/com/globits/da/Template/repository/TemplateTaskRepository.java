package com.globits.da.Template.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateTask;

@Repository
public interface TemplateTaskRepository extends JpaRepository<TemplateTask, UUID> {
	
}
