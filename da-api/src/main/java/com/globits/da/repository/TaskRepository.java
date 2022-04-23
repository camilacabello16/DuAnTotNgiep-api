package com.globits.da.repository;


import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,UUID> {
	
	@Query("select count(entity.id) FROM Task entity WHERE entity.card.id =?1")
	Long totalTaskByCard(UUID cardId);
	
	@Query("select count(entity.id) FROM Task entity WHERE entity.user.id =?1")
	Long totalTaskByUserId(Long userId);
}
