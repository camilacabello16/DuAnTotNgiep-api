package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.Card;
import com.globits.da.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team,UUID> {

}
