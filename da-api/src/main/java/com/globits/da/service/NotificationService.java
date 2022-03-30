package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Notification;
import com.globits.da.dto.NotificationDto;

public interface NotificationService extends GenericService<Notification, UUID> {
	public NotificationDto saveOrUpdate(UUID id,NotificationDto dto);
	public Boolean deleteNotification(UUID id);
	public List<NotificationDto> getListNotification();
}
