package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.da.domain.Card;
import com.globits.da.domain.Team;
import com.globits.da.domain.TeamUser;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser,UUID> {

}
