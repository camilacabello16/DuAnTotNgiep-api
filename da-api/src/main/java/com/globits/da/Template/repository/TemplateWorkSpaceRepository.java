package com.globits.da.Template.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.da.Template.domain.TemplateWorkSpace;

@Repository
public interface TemplateWorkSpaceRepository extends JpaRepository<TemplateWorkSpace, UUID> {

}
