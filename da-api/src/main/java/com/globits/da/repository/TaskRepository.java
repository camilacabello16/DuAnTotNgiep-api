package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,UUID> {

}
