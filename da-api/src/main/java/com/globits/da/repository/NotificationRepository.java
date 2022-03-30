package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Notification;
import com.globits.security.domain.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
