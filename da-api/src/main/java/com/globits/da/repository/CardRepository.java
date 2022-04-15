package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.Card;

@Repository
public interface CardRepository extends JpaRepository<Card,UUID> {
	
	@Query("select entity FROM Card entity WHERE entity.workSpace.id =?1")
	List<Card> getAllByWorkSpaceId(UUID workSoaceId);
}
